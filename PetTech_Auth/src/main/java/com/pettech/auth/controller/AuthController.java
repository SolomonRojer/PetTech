package com.pettech.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pettech.auth.service.AuthService;
import com.pettech.data.dto.UserDto;

@CrossOrigin
@RestController
@RequestMapping("/auth")

public class AuthController {
	
	@Autowired
	AuthService service;
	
	
	@PostMapping(value = "/add/users", produces = { "application/json" })
	public ResponseEntity<Object> createUsers(@RequestBody UserDto userDto) throws Exception {
		return  service.createUsers(userDto);
	}
	@PostMapping(value = "/login", produces = { "application/json" })
	public ResponseEntity<Object> userLogin(@RequestBody UserDto userDTO, HttpServletRequest request,
			HttpServletResponse response) {
		return service.validateLoginParam(userDTO);

	}
}
