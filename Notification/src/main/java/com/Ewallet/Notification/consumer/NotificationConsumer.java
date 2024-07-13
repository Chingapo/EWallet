package com.Ewallet.Notification.consumer;

import com.Ewallet.Notification.service.NotificationService;
import com.Ewallet.Notification.service.resource.NotificationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

	@Autowired
	NotificationService notificationService;

	ObjectMapper objectMapper = new ObjectMapper();
	Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

	@KafkaListener(topics = "notificationTopic", groupId = "notificationGrp")
	public void consume(String message) {
		System.out.println("Consumed message: " + message);
		try{
			NotificationRequest notificationRequest = objectMapper.readValue(message, NotificationRequest.class);
			notificationService.sendNotification(notificationRequest);
		}catch(Exception e){
			logger.error("exception while reading notification"+ e.getMessage());
		}

	}

}
