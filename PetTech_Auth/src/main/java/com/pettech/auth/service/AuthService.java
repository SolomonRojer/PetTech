package com.pettech.auth.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pettech.data.dto.UserDto;

@Service
public interface AuthService  {

	ResponseEntity<Object> createUsers(UserDto userDto);

	ResponseEntity<Object> validateLoginParam(UserDto userDTO);

}
