package rafalotto.picpay.service;

import org.springframework.stereotype.Service;
import rafalotto.picpay.dto.CreateWalletDto;
import rafalotto.picpay.entity.Wallet;
import rafalotto.picpay.exception.WalletDataAlreadyExistException;
import rafalotto.picpay.repository.WalletRepository;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletDto dtoWallet) {

        var walletDb = walletRepository.findByCpfCnpjOrEmail(dtoWallet.cpfCnpj(), dtoWallet.email());
        if(walletDb.isPresent()) {
           throw new WalletDataAlreadyExistException("CpfCnpj or email already exist");
        }
       return walletRepository.save(dtoWallet.toWallet());
    }
}
