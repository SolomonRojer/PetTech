package com.pettech.auth.serviceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pettech.auth.responseDTO.loginResponse;
import com.pettech.auth.service.AuthService;
import com.pettech.data.dto.UserDto;
import com.pettech.data.jwt.JwtUtils;
import com.pettech.data.model.ERole;
import com.pettech.data.model.Role;
import com.pettech.data.model.userDetails;
import com.pettech.data.repo.RoleRepository;
import com.pettech.data.repo.UserRepository;
import com.pettech.data.response.MessageResponse;
import com.pettech.data.response.service.UserDetailsImpl;

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
	@Autowired
	Environment env;

	@Override
	public ResponseEntity<Object> createUsers(UserDto userDto) {
		try {
			Set<Role> roles = new HashSet<>();

			Optional<userDetails> users = userRepo.findByEmail(userDto.getEmail());
			if (users.isPresent()) {
				return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.PARTIAL_CONTENT.value())
						.message(env.getProperty("email.already.exists")).build());
			}
			if (userDto.getUserType().equalsIgnoreCase("ROLE_USER")) {
				Role empRole = roleRepo.findByName(ERole.ROLE_USER);
				roles.add(empRole);
			} else if (userDto.getUserType().equalsIgnoreCase("ROLE_ADMIN")) {
				Role empRole = roleRepo.findByName(ERole.ROLE_ADMIN);
				roles.add(empRole);
			}

			if (!(userDto.getPassword().equals(userDto.getConfirmPassword()))) {
				return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.OK.value())
						.message(env.getProperty("password.does.not.match")).build());
			}
			userDetails user = userDetails.builder().name(userDto.getName()).email(userDto.getEmail())
					.userStatus("ACTIVE").password(encoder.encode(userDto.getPassword())).gender(userDto.getGender())
					.number(userDto.getNumber()).userName(userDto.getUserName()).address(userDto.getAddress())
					.discription(userDto.getDiscription()).roles(roles).build();

			userRepo.save(user);
			return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.OK.value())
					.message(env.getProperty("user.added.sucessfully")).build());

		} catch (Exception e) {
			return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.PARTIAL_CONTENT.value())
					.message(env.getProperty("problem.adding.user")).build());
		}
	}

	@Override
	public ResponseEntity<Object> validateLoginParam(UserDto userDTO) {
		try {
			Optional<userDetails> users = userRepo.findByEmail(userDTO.getEmail());
			if ((users.get().getUserStatus().equals("INACTIVE")) || (users.get().getUserStatus() == null)) {
				return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.PARTIAL_CONTENT.value())
						.message(env.getProperty("Unauthorized.User")).build());
			}

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userDTO.getUserName(), userDTO.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			if (Objects.isNull(userDetails)) {
				throw new RuntimeException("User not found");
			}

			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());
			users.get().setUserStatus("ACTIVE");

			return ResponseEntity.ok(loginResponse.builder().jwtToken("bearer token:" + jwt)
					.email(users.get().getEmail()).name(users.get().getName()).userName(users.get().getUserName())
					.number(users.get().getNumber()).userStatus(users.get().getUserStatus()).build());

		} catch (Exception e) {
			return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.BAD_REQUEST.value())
					.message(env.getProperty("Bad.requst")).build());
		}
	}

}
