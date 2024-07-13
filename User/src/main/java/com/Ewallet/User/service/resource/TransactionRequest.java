package com.Ewallet.User.service.resource;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
	private Long recieverId;
	private Double amount;
	private String description;
	private String transactionType;

}
