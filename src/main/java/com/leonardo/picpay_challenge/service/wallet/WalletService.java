package com.leonardo.picpay_challenge.service.wallet;

import com.leonardo.picpay_challenge.domain.transaction.Transaction;
import com.leonardo.picpay_challenge.domain.wallet.Wallet;
import com.leonardo.picpay_challenge.domain.wallet.WalletType;
import com.leonardo.picpay_challenge.exception.InvalidTransactionException;
import com.leonardo.picpay_challenge.repository.wallet.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;


    public Optional<Wallet> findById(Long id){
        return this.walletRepository.findById(id);
    }

    public void saveWallet(Wallet wallet){
        this.walletRepository.save(wallet);
    }

    public void validateWallet(Transaction transaction){
        this.walletRepository.findById(transaction.payee())
                .map(payee -> walletRepository.findById(transaction.payer())
                     .map(payer -> isValid(transaction, payer) ? transaction : null)
                     .orElseThrow(() -> new InvalidTransactionException("Invalid transaction - %s".formatted(transaction))))
                .orElseThrow(() -> new InvalidTransactionException("Invalid transaction - %s".formatted(transaction)));
    }

    private static boolean isValid(Transaction transaction, Wallet payer) {
        return payer.type() == WalletType.COMUM.getValue() &&
                payer.balance().compareTo(transaction.value()) >= 0 &&
                !payer.id().equals(transaction.payee());
    }
}
