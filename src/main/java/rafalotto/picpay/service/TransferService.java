package rafalotto.picpay.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import rafalotto.picpay.dto.TransferDto;
import rafalotto.picpay.entity.Transfer;
import rafalotto.picpay.entity.Wallet;
import rafalotto.picpay.exception.InsufficientBalanceException;
import rafalotto.picpay.exception.TransferNotAllowedException;
import rafalotto.picpay.exception.TransferNotAuthorizedException;
import rafalotto.picpay.exception.WalletNotFoundException;
import rafalotto.picpay.repository.TransferRepository;
import rafalotto.picpay.repository.WalletRepository;

import java.util.concurrent.CompletableFuture;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;
    private final WalletRepository walletRepository;

    public TransferService(AuthorizationService authorizationService, TransferRepository transferRepository, NotificationService notificationService, WalletRepository walletRepository) {
        this.authorizationService = authorizationService;
        this.transferRepository = transferRepository;
        this.notificationService = notificationService;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Transfer transfer(TransferDto transferDto) {
        var sender = walletRepository.findById(transferDto.payer())
                .orElseThrow(()-> new WalletNotFoundException(transferDto.payer()));

        var receiver = walletRepository.findById(transferDto.payee())
                .orElseThrow(()-> new WalletNotFoundException(transferDto.payee()));

        validateTransfer(transferDto, sender);

        sender.debit(transferDto.value());
        receiver.credit(transferDto.value());

        var transfer = new Transfer(sender,receiver,transferDto.value());
        walletRepository.save(sender);
        walletRepository.save(receiver);
        var transferResult = transferRepository.save(transfer);

        CompletableFuture.runAsync(()-> notificationService.sendNotification(transferResult));

        return transferResult;
    }
    private void validateTransfer(TransferDto transferDto, Wallet sender) {
        if (!sender.isTransferAllowedForWalletType()) throw new TransferNotAllowedException();
        if(!sender.isBalancerEqualOrGreaterThan(transferDto.value())) throw new InsufficientBalanceException();
        if(!authorizationService.isAuthorized(transferDto)) throw new TransferNotAuthorizedException();

    }
}
