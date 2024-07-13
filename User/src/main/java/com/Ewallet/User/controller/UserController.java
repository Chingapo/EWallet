package com.Ewallet.User.controller;


import com.Ewallet.User.domain.User;
import com.Ewallet.User.service.UserService;
import com.Ewallet.User.service.resource.TransactionRequest;
import com.Ewallet.User.service.resource.UserRequest;
import com.Ewallet.User.service.resource.UserResponse;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("user")
	public ResponseEntity<User> createUser(@RequestBody @Valid UserRequest userRequest){
		userService.createUser(userRequest.toUser());
		return new ResponseEntity<>(userRequest.toUser(),HttpStatus.CREATED);
	}

	@GetMapping("user/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable("id") String id){
		UserResponse userResponse = userService.getUser(id);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	@DeleteMapping("user/{id}")
	public ResponseEntity<UserResponse> deleteUser(@PathVariable("id") String id){
		User user = userService.deleteUser(String.valueOf(id));
		return new ResponseEntity<>(new UserResponse(user), HttpStatus.OK);
	}

	@PostMapping("user/{user-id}/transfer")
	public ResponseEntity<?> performTransaction(@PathVariable("user-id") String userId, @RequestBody @Valid TransactionRequest transactionRequest){
		boolean success = userService.transfer(Long.valueOf(userId), transactionRequest);
		if(success){
			return new ResponseEntity<>(success, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(success, HttpStatus.BAD_REQUEST);
		}
	}

}
