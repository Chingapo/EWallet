package com.Ewallet.Wallet.service.impl;

import com.Ewallet.Wallet.domain.TransactionType;
import com.Ewallet.Wallet.domain.Wallet;
import com.Ewallet.Wallet.exception.WalletException;
import com.Ewallet.Wallet.repository.WalletRepository;
import com.Ewallet.Wallet.service.WalletService;
import com.Ewallet.Wallet.service.resource.WalletTransactionRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WalletServiceImplTest {

	/**
	 * Mockito -> Library helps mocking objects or beans for testing.
	 *
	 * Steps for testing:
	 * 1.Mock -> we mock the object we want to test.
	 * 2.Stub -> we mock behaviour of the objects when the methods are called.
	 * 3.Spy -> helps in stubbing when the object is not mocked.
	 *
	 *
	 *
	 */

	@Test
	void performTransactionTestDeposit() {

		WalletServiceImpl walletService = new WalletServiceImpl();
		WalletRepository walletRepository = mock(WalletRepository.class);
		walletService.setWalletRepository(walletRepository);

		Wallet wallet = new Wallet();
		wallet.setBalance(40.0);
		wallet.setUserId(1L);
		wallet.setId(1L);

		Wallet expectedWallet = new Wallet();
		expectedWallet.setBalance(50.0);
		expectedWallet.setUserId(1L);
		expectedWallet.setId(1L);


		WalletTransactionRequest walletTransactionRequest = new WalletTransactionRequest();
		walletTransactionRequest.setAmount(10.0);
		walletTransactionRequest.setRecieverId(1L);
		walletTransactionRequest.setSenderId(1L);
		walletTransactionRequest.setTransactionType(TransactionType.DEPOSIT.name());


		when(walletRepository.findByUserId(anyLong())).thenReturn(wallet);
		when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

		walletService.performTransaction(walletTransactionRequest);

		assertNotNull(wallet);

		assertEquals(expectedWallet.getBalance(), wallet.getBalance());

	}

	@Test
	void performTransactionForInvalidWallet(){
		WalletServiceImpl walletService = new WalletServiceImpl();
		WalletRepository walletRepository = mock(WalletRepository.class);
		walletService.setWalletRepository(walletRepository);

		WalletTransactionRequest walletTransactionRequest = new WalletTransactionRequest();
		walletTransactionRequest.setAmount(10.0);
		walletTransactionRequest.setRecieverId(1L);
		walletTransactionRequest.setSenderId(1L);
		walletTransactionRequest.setTransactionType(TransactionType.DEPOSIT.name());

		when(walletRepository.findByUserId(anyLong())).thenReturn(null);

		assertThrows(WalletException.class, () -> {
			walletService.performTransaction(walletTransactionRequest);
		});
	}


}