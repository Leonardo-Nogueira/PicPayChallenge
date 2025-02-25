package com.leonardo.picpay_challenge.repository.transaction;

import com.leonardo.picpay_challenge.domain.transaction.Transaction;
import org.springframework.data.repository.ListCrudRepository;

public interface TransactionRepository extends ListCrudRepository<Transaction,Long> {
}
