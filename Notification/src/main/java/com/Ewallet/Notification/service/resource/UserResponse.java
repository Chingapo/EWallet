package com.Ewallet.Notification.service.resource;

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

}
