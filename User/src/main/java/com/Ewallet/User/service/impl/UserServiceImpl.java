package com.Ewallet.User.service.impl;

import com.Ewallet.User.domain.User;
import com.Ewallet.User.exception.UserException;
import com.Ewallet.User.repository.UserRepository;
import com.Ewallet.User.service.UserService;
import com.Ewallet.User.service.resource.TransactionRequest;
import com.Ewallet.User.service.resource.UserRequest;
import com.Ewallet.User.service.resource.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	@Value("${kafka.topic.user-created}")
	private String USER_CREATED_TOPIC;

	@Value("${kafka.topic.user-deleted}")
	private String USER_DELETED_TOPIC;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	RestTemplate restTemplate;


	@Override
	public void createUser(User user) throws UserException {
		Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
		//check if username already exists
		if(optionalUser.isPresent()){
			throw new UserException("EWALLET_USER_EXISTS_EXCEPTION", "Username already exists");
		}
		//encode the password
		user.setPassword(encoder.encode(user.getPassword()));
		//add to database
		userRepository.save(user);

		// publish event of user creation
		kafkaTemplate.send(USER_CREATED_TOPIC, String.valueOf(user.getId()));

	}

	@Override
	public UserResponse getUser(String id) throws UserException {
		Optional<User> userOptional = userRepository.findById(Long.valueOf(id));
		User user = userOptional.orElseThrow(() -> new UserException("EWALLET_USER_NOT_FOUND_EXCEPTION", "User not found"));
		return new UserResponse(user);
	}

	@Override
	public User deleteUser(String id) {
		Optional<User> userOptional = userRepository.findById(Long.valueOf(id));
		if(userOptional.isEmpty()){
			throw new UserException("EWALLET_USER_NOT_FOUND_EXCEPTION", "User not found");
		}
		userRepository.deleteById(Long.valueOf(id));
		kafkaTemplate.send(USER_DELETED_TOPIC, id);
		return userOptional.get();
	}

	@Override
	public User updateUser(UserRequest userRequest, String id) {
		Optional<User> optionalUser = userRepository.findById(Long.valueOf(id));
		if(optionalUser.isEmpty()){
			throw new UserException("EWALLET_USER_NOT_FOUND_EXCEPTION", "User not found");
		}
		validateChange(optionalUser.get(), userRequest);

		User user = optionalUser.get();
		user.setEmail(userRequest.getEmail());
		user.setUsername(userRequest.getUsername());
		//encode the password
		user.setPassword(encoder.encode(userRequest.getPassword()));
		return userRepository.save(optionalUser.get());
	}

	private void validateChange(User user, UserRequest userRequest) {
		if(user.getUsername().equals(userRequest.getUsername()) && user.getEmail().equals(userRequest.getEmail()) && user.getPassword().equals(userRequest.getPassword())){
			throw new UserException("EWALLET_USER_NOT_CHANGED_EXCEPTION", "New details are same as old details");
		}
	}

	@Override
	public boolean transfer(Long userId, TransactionRequest transactionRequest) {
		Optional<User> senderOptional = userRepository.findById(userId);
		if(senderOptional.isEmpty()){
			throw new UserException("EWALLET_USER_NOT_FOUND_EXCEPTION", "Sender not found");
		}
		Optional<User> recieverOptional = userRepository.findById(transactionRequest.getRecieverId());
		if(recieverOptional.isEmpty()){
			throw new UserException("EWALLET_RECIEVER_NOT_FOUND_EXCEPTION", "Reciever not found");
		}
		ResponseEntity<Boolean> response = restTemplate.postForEntity("http://TRANSACTION/transaction/"+userId,transactionRequest,Boolean.class);
		return response.getBody();

	}
}
