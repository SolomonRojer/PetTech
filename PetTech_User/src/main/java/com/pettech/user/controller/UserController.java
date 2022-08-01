package com.pettech.user.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pettech.data.model.ProductDetails;
import com.pettech.data.model.userDetails;
import com.pettech.user.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/profile/details/{id}")
	public ResponseEntity<Optional<userDetails>> profileDetails(@PathVariable String id) {
		return userService.getDetails(id);

	}

	@PostMapping("/add/pi")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) {
		return userService.upDateUser(file, id);
	}

	@PostMapping("/add/doc")
	public ResponseEntity<?> upDateProfile(@RequestBody userDetails userDetails) {
		return userService.upDate(userDetails);
	}
	
	@PostMapping("/sale/pet")
	public ResponseEntity<?> upLoadPet( @RequestPart("petDetails") String petDetails, @RequestPart("file") List<MultipartFile> file) throws JsonMappingException, JsonProcessingException {
		ObjectMapper op = new ObjectMapper();
		return userService.upLoad(op.readValue(petDetails, ProductDetails.class), file);
	}
	
	@GetMapping("/my/sale/details/{id}/{petId}")
	public ResponseEntity<List<ProductDetails>> mySaleDetails(@PathVariable String id, @PathVariable String petId) {
		return userService.getSaleDetails(id, petId);

	}
	
	@GetMapping("/sale/post/list")
	public ResponseEntity<?> listHomePost() {
		return userService.listHomePost();

	}
}
