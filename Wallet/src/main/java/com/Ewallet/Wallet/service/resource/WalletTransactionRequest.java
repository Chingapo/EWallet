package com.Ewallet.Wallet.service.resource;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransactionRequest {

	private Long senderId;
	private Long recieverId;
	private Double amount;
	private String description;
	private String transactionType;
}
