package com.Ewallet.Transaction.service.resource;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequest {

	private Long userId;
	private Double amount;
	private String userType;
	private String transactionStatus;


}
