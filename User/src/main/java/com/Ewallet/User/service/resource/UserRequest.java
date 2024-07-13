package com.Ewallet.User.service.resource;


import com.Ewallet.User.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

	@NotBlank(message = "Username cannot be blank")
	private String username;
	@Email(message = "Email is not valid")
	private String email;
	@NotBlank(message = "Password cannot be blank")
	private String password;

	public User toUser(){
		return User.builder()
				.username(username)
				.email(email)
				.password(password)
				.build();
	}
}
