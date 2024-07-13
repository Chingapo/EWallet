package com.Ewallet.User.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class APIConfiguration {

	@Bean
	@LoadBalanced//leverages eureka server to load urls from service registry rather than manually loading them
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
