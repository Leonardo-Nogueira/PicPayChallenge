package com.leonardo.picpay_challenge.repository.wallet;

import com.leonardo.picpay_challenge.domain.wallet.Wallet;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet,Long> {
}
