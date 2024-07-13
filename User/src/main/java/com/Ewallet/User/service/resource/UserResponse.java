package com.Ewallet.User.service.resource;


import com.Ewallet.User.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

	private String id;
	private String username;
	private String email;

	public UserResponse(User user) {
		this.id = String.valueOf(user.getId());
		this.username = user.getUsername();
		this.email = user.getEmail();
	}


}
