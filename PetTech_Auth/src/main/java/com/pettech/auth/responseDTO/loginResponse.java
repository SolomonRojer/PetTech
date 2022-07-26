package com.pettech.auth.responseDTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class loginResponse {

	private String userStatus;
	private String name;
	private String userName;
	private String email;
	private String jwtToken;
	private String number;
}
