package com.Ewallet.Transaction.service.resource;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletTransactionRequest {

	private Long senderId;
	private Long recieverId;
	private Double amount;
	private String description;
	private String transactionType;

}
