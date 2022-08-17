package com.pettech.user.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.pettech.data.response.MessageResponse;
import com.pettech.user.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

//	get user details by id
	@GetMapping("/profile/details/{id}")
	public ResponseEntity<?> profileDetails(@PathVariable String id) {
		return userService.getDetails(id);

	}

//	profile photo update
	@PostMapping("/profile/photo/update")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestPart("userDetails") String userDetails, @RequestPart("id") String id)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper op = new ObjectMapper();
		return userService.upDateUser(file, op.readValue(userDetails, userDetails.class), id);
	}

//	first time user add profile photo for user
	@PostMapping("/add/doc")
	public ResponseEntity<?> upDateProfile(@RequestParam("file") MultipartFile file, @RequestParam("id") String id)
			throws IOException {
		return userService.upDate(file, id);
	}

//	new pet sale API
	@PostMapping("/sale/pet")
	public ResponseEntity<?> upLoadPet(@RequestPart("petDetails") String petDetails,
			@RequestPart("file") List<MultipartFile> file) throws JsonMappingException, JsonProcessingException {
		ObjectMapper op = new ObjectMapper();
		return userService.upLoad(op.readValue(petDetails, ProductDetails.class), file);
	}

//	on profile my sale times list
	@GetMapping("/my/sale/details/{id}/{petId}")
	public ResponseEntity<?> mySaleDetails(@PathVariable String id, @PathVariable String petId) {
		return userService.getSaleDetails(id, petId);
	}

//	on sale home page
	@GetMapping("/sale/post/list")
	public ResponseEntity<?> listHomePost() {
		return userService.listHomePost();
	}

//	detele sale pet post
	@DeleteMapping(value = "/delete/sale/pet/{id}", produces = { "application/json" })
	public ResponseEntity<MessageResponse> deleteSalePet(@PathVariable("id") String id) throws Exception {
		return userService.deleteSalePet(id);
	}

}
