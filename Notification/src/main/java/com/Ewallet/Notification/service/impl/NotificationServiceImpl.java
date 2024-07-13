package com.Ewallet.Notification.service.impl;

import com.Ewallet.Notification.config.RestConfig;
import com.Ewallet.Notification.service.MailContentUtil;
import com.Ewallet.Notification.service.NotificationService;
import com.Ewallet.Notification.service.resource.NotificationRequest;
import com.Ewallet.Notification.service.resource.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationServiceImpl implements NotificationService {


	@Autowired
	RestTemplate restTemplate;

	@Autowired
	JavaMailSender mailSender;

	@Override
	public void sendNotification(NotificationRequest notificationRequest) {

		if(notificationRequest.getTransactionStatus().equalsIgnoreCase("SUCCESS") && notificationRequest.getUserType().equalsIgnoreCase("SENDER")){

			UserResponse response = restTemplate.getForEntity("http://localhost:8081/user/"+notificationRequest.getUserId(),UserResponse.class).getBody();
			SimpleMailMessage message = new SimpleMailMessage();
			message.setSubject(MailContentUtil.getSubjectTransactionSuccessful());
			message.setText(MailContentUtil.getSuccessSenderEmailContent(response.getUsername(),notificationRequest.getAmount()));
			message.setTo(response.getEmail());
			mailSender.send(message);
		}

		if(notificationRequest.getTransactionStatus().equalsIgnoreCase("SUCCESS") && notificationRequest.getUserType().equalsIgnoreCase("RECIEVER")){

			UserResponse response = restTemplate.getForEntity("http://localhost:8081/user/"+notificationRequest.getUserId(),UserResponse.class).getBody();
			SimpleMailMessage message = new SimpleMailMessage();
			message.setSubject(MailContentUtil.getSubjectTransactionSuccessful());
			message.setText(MailContentUtil.getSuccessRecieverEmailContent(response.getUsername(),notificationRequest.getAmount()));
			message.setTo(response.getEmail());
			mailSender.send(message);
		}
		if(notificationRequest.getTransactionStatus().equalsIgnoreCase("FAILURE") && notificationRequest.getUserType().equalsIgnoreCase("SENDER")){

			UserResponse response = restTemplate.getForEntity("http://localhost:8081/user/"+notificationRequest.getUserId(),UserResponse.class).getBody();
			SimpleMailMessage message = new SimpleMailMessage();
			message.setSubject(MailContentUtil.getSubjectTransactionFailure());
			message.setText(MailContentUtil.getFailureEmailContent(response.getUsername(),notificationRequest.getAmount()));
			message.setTo(response.getEmail());
			mailSender.send(message);
		}


	}
}
