package com.Ewallet.Wallet.service.impl;

import com.Ewallet.Wallet.domain.TransactionType;
import com.Ewallet.Wallet.domain.Wallet;
import com.Ewallet.Wallet.exception.WalletException;
import com.Ewallet.Wallet.repository.WalletRepository;
import com.Ewallet.Wallet.service.WalletService;
import com.Ewallet.Wallet.service.resource.WalletResponse;
import com.Ewallet.Wallet.service.resource.WalletTransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

	private Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

	@Autowired
	WalletRepository walletRepository;

	public void setWalletRepository(WalletRepository walletRepository) {
		this.walletRepository = walletRepository;
	}

	@Override
	public void createWallet(Long userId) {
		try{
			Wallet userWallet = walletRepository.findByUserId(userId);
			if(Objects.nonNull(userWallet)){
				logger.info("Wallet already exists for user: {}", userId);
				return;
			}
			Wallet wallet = new Wallet();
			wallet.setUserId(userId);
			wallet.setBalance(0.0);
			walletRepository.save(wallet);
		}catch(Exception e){
			logger.error("Exception while creating Wallet: {}",e.getMessage());
		}
	}

	@Override
	public Wallet deleteWallet(Long userId) {
		try{
			Wallet userWallet = walletRepository.findByUserId(userId);
			if(Objects.nonNull(userWallet)){
				walletRepository.delete(userWallet);
				return userWallet;
			}else {
				logger.info("Wallet does not exist for user: {}", userId);
				return null;
			}
		}catch(Exception e){
			logger.error("Exception while deleting Wallet: {}",e.getMessage());
			return null;
		}
	}

	@Override
	public WalletResponse getWallet(Long userId) {
		Wallet userWallet = walletRepository.findByUserId(userId);
		if(Objects.nonNull(userWallet)){
			logger.info("Wallet found for user: {}", userId);
			return new WalletResponse(userWallet);
		}else {
			throw new WalletException("EWALLET_WALLET_NOT_FOUND", "Wallet not found for user: " + userId);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = WalletException.class)  //Spring JPA transactional property
	public boolean performTransaction(WalletTransactionRequest walletTransactionRequest) {

		Wallet senderWallet = walletRepository.findByUserId(walletTransactionRequest.getSenderId());
		Wallet recieverWallet = walletRepository.findByUserId(walletTransactionRequest.getRecieverId());

		if (TransactionType.DEPOSIT.name().equals(walletTransactionRequest.getTransactionType())){
			if(Objects.isNull(recieverWallet)){
				throw new WalletException("EWALLET_WALLET_NOT_FOUND", "Wallet not found for user: " + walletTransactionRequest.getSenderId());
			}

			updateWallet(recieverWallet, walletTransactionRequest.getAmount());
			return true;
		} else if (TransactionType.WITHDRAW.name().equals(walletTransactionRequest.getTransactionType())){
			if(Objects.isNull(recieverWallet)){
				throw new WalletException("EWALLET_WALLET_NOT_FOUND", "Wallet not found for user: " + walletTransactionRequest.getSenderId());
			}

			updateWallet(recieverWallet, -1*walletTransactionRequest.getAmount());
			return true;
		} else if (TransactionType.TRANSFER.name().equals(walletTransactionRequest.getTransactionType())) {
			try {
				if(Objects.isNull(senderWallet) || Objects.isNull(recieverWallet)){
					throw new WalletException("EWALLET_WALLET_NOT_FOUND", "Wallet not found for user: " + walletTransactionRequest.getSenderId());
				}
				handleTransaction(senderWallet, recieverWallet, walletTransactionRequest.getAmount());
				return true;
			} catch (WalletException e) {
				logger.error("Exception while performing transaction: {}",e.getMessage());
				throw e;
			}
		} else {
			throw new WalletException("EWALLET_INVALID_TRANSACTION_TYPE", "Invalid Transaction Type");
		}
	}

	private void updateWallet(Wallet wallet, Double amount) {

		//transfer from bank to wallet
		wallet.setBalance(wallet.getBalance() + amount);
		walletRepository.save(wallet);
	}

	private void handleTransaction(Wallet senderWallet, Wallet recieverWallet, Double amount) {
		try{
			Wallet senderCopy = new Wallet();
			BeanUtils.copyProperties(senderWallet, senderCopy);
			Wallet recieverCopy = new Wallet();
			BeanUtils.copyProperties(recieverWallet, recieverCopy);

			if(senderCopy.getBalance() < amount){
				throw new WalletException("EWALLET_INSUFFICIENT_BALANCE", "Insufficient Balance");
			}
			senderCopy.setBalance(senderCopy.getBalance() - amount);
			recieverCopy.setBalance(recieverCopy.getBalance() + amount);
			walletRepository.save(senderCopy);
			walletRepository.save(recieverCopy);

		} catch(Exception e){
			throw new WalletException("EWALLET_TRANSACTION_FAILED", "Transaction Failed: " + e.getMessage());
		}
	}


}
