package com.Ewallet.User.service;

import com.Ewallet.User.domain.User;
import com.Ewallet.User.exception.UserException;
import com.Ewallet.User.service.resource.TransactionRequest;
import com.Ewallet.User.service.resource.UserRequest;
import com.Ewallet.User.service.resource.UserResponse;
import org.springframework.stereotype.Service;


public interface UserService {


	public void createUser(User user);

	public UserResponse getUser(String id);

	public User deleteUser(String id);

	public User updateUser(UserRequest userRequest, String id);

	public boolean transfer(Long userId, TransactionRequest transactionRequest);




}
