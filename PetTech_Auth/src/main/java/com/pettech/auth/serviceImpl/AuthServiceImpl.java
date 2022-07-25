package com.pettech.auth.serviceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pettech.data.dto.UserDto;
import com.pettech.data.jwt.JwtUtils;
import com.pettech.data.model.ERole;
import com.pettech.data.model.Role;
import com.pettech.data.model.userDetails;
import com.pettech.data.repo.RoleRepository;
import com.pettech.data.repo.UserRepository;
import com.pettech.data.response.MessageResponse;
import com.pettech.data.response.service.UserDetailsImpl;
import com.pettech.auth.service.AuthService;


@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	PasswordEncoder encoder;
	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtUtils jwtUtils;
	
	@Override
	public ResponseEntity<Object> createUsers(UserDto userDto) {
		try {
			Set<Role> roles = new HashSet<>();

			if (userDto.getUserType().equalsIgnoreCase("ROLE_USER")) {
				Role empRole = roleRepo.findByName(ERole.ROLE_USER);
				roles.add(empRole);
			}else if(userDto.getUserType().equalsIgnoreCase("ROLE_ADMIN")) {
				Role empRole = roleRepo.findByName(ERole.ROLE_ADMIN);
				roles.add(empRole);
			}
			userDetails user=  userDetails.builder().name(userDto.getName()).email(userDto.getEmail()).password( encoder.encode(userDto.getPassword())).confirmPassword(userDto.getConfirmPassword()).gender(userDto.getGender()).number(userDto.getNumber()).userName(userDto.getUserName()).roles(roles).build();
			
			userRepo.save(user);
			return ResponseEntity.ok(MessageResponse.builder().status("user added sucessfully")
					.response(HttpStatus.OK.value()).data(user).build());
			
			
		}catch (Exception e) {
			return ResponseEntity.ok(MessageResponse.builder().response(HttpStatus.BAD_REQUEST.value())
					.status("problem adding user").build());		}
	}

	@Override
	public ResponseEntity<Object> validateLoginParam(UserDto userDTO) {
	try {
		userDetails users = userRepo.findByEmail(userDTO.getEmail());
		if (!(userDTO.getPassword() != null
				&& userDTO.getPassword().equalsIgnoreCase(users.getConfirmPassword()))) {
			return ResponseEntity.ok(MessageResponse.builder().status("wrong password")
					.response(HttpStatus.BAD_GATEWAY.value()).build());		}
		
		String encryptedPassword = encoder.encode(userDTO.getPassword());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userDTO.getUserName(), userDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		System.out.println("bearer token:" + jwt);
		users.setUserStatus("ACTIVE");
		userRepo.save(users);
		return ResponseEntity.ok(MessageResponse.builder().status(jwt)
				.response(HttpStatus.OK.value()).data(users).build());
	}catch (Exception e) {
		System.out.println(e.getLocalizedMessage());
		return ResponseEntity.ok(MessageResponse.builder().response(HttpStatus.BAD_REQUEST.value())
				.status("Login Failed").build());		}	
	}

}
