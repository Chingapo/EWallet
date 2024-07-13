package com.Ewallet.Transaction.service.impl;


import com.Ewallet.Transaction.service.TransactionService;
import com.Ewallet.Transaction.service.resource.NotificationRequest;
import com.Ewallet.Transaction.service.resource.TransactionRequest;
import com.Ewallet.Transaction.service.resource.WalletTransactionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	RestTemplate restTemplate;

	public ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;


	@Override
	public boolean performTransaction(Long userId, TransactionRequest transactionRequest) throws JsonProcessingException {
		try {
			WalletTransactionRequest walletTransactionRequest = new WalletTransactionRequest();

			walletTransactionRequest.setSenderId(userId);
			walletTransactionRequest.setRecieverId(transactionRequest.getRecieverId());
			walletTransactionRequest.setAmount(transactionRequest.getAmount());
			walletTransactionRequest.setDescription(transactionRequest.getDescription());
			walletTransactionRequest.setTransactionType(transactionRequest.getTransactionType());

			String url = "http://localhost:8082/wallet/transaction";

			ResponseEntity<Boolean> response = restTemplate.postForEntity(url, walletTransactionRequest, Boolean.class);
			String content = Strings.EMPTY;
			if (response.getStatusCode().is2xxSuccessful()) {
				NotificationRequest senderNotificationRequest = new NotificationRequest();
				senderNotificationRequest.setTransactionStatus("SUCCESS");
				senderNotificationRequest.setAmount(transactionRequest.getAmount());
				senderNotificationRequest.setUserId(userId);
				senderNotificationRequest.setUserType("SENDER");
				content = objectMapper.writeValueAsString(senderNotificationRequest);
				//send event to sender on success
				kafkaTemplate.send("notificationTopic", content);

				NotificationRequest receiverNotificationRequest = new NotificationRequest();
				receiverNotificationRequest.setTransactionStatus("SUCCESS");
				receiverNotificationRequest.setAmount(transactionRequest.getAmount());
				receiverNotificationRequest.setUserId(transactionRequest.getRecieverId());
				receiverNotificationRequest.setUserType("RECIEVER");
				content = objectMapper.writeValueAsString(receiverNotificationRequest);
				//send event to sender on success
				kafkaTemplate.send("notificationTopic", content);

			} else {
				NotificationRequest senderNotificationRequest = new NotificationRequest();
				senderNotificationRequest.setTransactionStatus("FAILURE");
				senderNotificationRequest.setAmount(transactionRequest.getAmount());
				senderNotificationRequest.setUserId(userId);
				senderNotificationRequest.setUserType("USER");
				content = objectMapper.writeValueAsString(senderNotificationRequest);
				//send event to sender on success
				kafkaTemplate.send("notificationTopic", content);
			}

			return response.getStatusCode().is2xxSuccessful();
		}catch(JsonProcessingException e){
			return false;
		}
	}
}
