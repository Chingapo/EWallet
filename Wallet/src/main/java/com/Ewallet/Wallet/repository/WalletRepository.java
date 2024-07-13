package com.Ewallet.Wallet.repository;

import com.Ewallet.Wallet.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

	Wallet findByUserId(Long userId);

	Optional<Wallet> findById(Long id);


}
