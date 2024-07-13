package com.Ewallet.Notification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	@Bean
	public JavaMailSender createMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("arjunpareek3000@gmail.com");
		mailSender.setPassword("weiu wlhg pcwa isst");
		mailSender.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
		mailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
		return mailSender;
	}

}
