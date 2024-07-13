package com.Ewallet.Wallet.service;


import com.Ewallet.Wallet.domain.Wallet;
import com.Ewallet.Wallet.service.resource.WalletResponse;
import com.Ewallet.Wallet.service.resource.WalletTransactionRequest;
import org.springframework.stereotype.Service;

@Service
public interface WalletService {

	public void createWallet(Long userId);

	public Wallet deleteWallet(Long userId);

	public WalletResponse getWallet(Long userId);

	public boolean performTransaction(WalletTransactionRequest walletTransactionRequest);

}
