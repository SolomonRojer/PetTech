package com.pettech.data.dto;

import lombok.Data;

@Data
public class UserDto {
	private String name;
	private String userName;
	private String email;
	private String number;
	private String gender;
	private String password;
	private String confirmPassword;
	public enum ERole {
		ROLE_USER,
	    
	    ROLE_ADMIN
	}
	private String userType;

}