package rafalotto.picpay.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rafalotto.picpay.dto.CreateWalletDto;
import rafalotto.picpay.entity.Wallet;
import rafalotto.picpay.service.WalletService;

@RestController
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/wallets")
    public ResponseEntity<Wallet> createWallet(@RequestBody @Valid CreateWalletDto dtoWallet) {
       var wallet = walletService.createWallet(dtoWallet);

       return ResponseEntity.ok(wallet);
    }
}
