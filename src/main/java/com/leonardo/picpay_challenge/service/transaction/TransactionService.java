package com.leonardo.picpay_challenge.service.transaction;


import com.leonardo.picpay_challenge.domain.transaction.Transaction;
import com.leonardo.picpay_challenge.repository.transaction.TransactionRepository;
import com.leonardo.picpay_challenge.service.authorization.AuthorizationService;
import com.leonardo.picpay_challenge.service.notification.NotificationService;
import com.leonardo.picpay_challenge.service.wallet.WalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletService walletService;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;

    public TransactionService(TransactionRepository transactionRepository,
                              WalletService walletService,
                              AuthorizationService authorizationService,
                              NotificationService notificationService){
        this.transactionRepository = transactionRepository;
        this.walletService = walletService;
        this.authorizationService = authorizationService;
        this.notificationService=notificationService;
    }

    @Transactional
    public Transaction createTransaction(Transaction transaction){

        walletService.validateWallet(transaction);

        var newTransaction = transactionRepository.save(transaction);
        var wallet = walletService.findById(transaction.payer()).get();
        var walletPayee = walletService.findById(transaction.payee()).get();

        walletService.saveWallet(wallet.debit(transaction.value()));
        walletService.saveWallet(walletPayee.credit(transaction.value()));

        authorizationService.authorize(transaction);

        notificationService.notify(transaction);

        return newTransaction;
    }

    public List<Transaction> getTransactions() {
        return this.transactionRepository.findAll();
    }
}
