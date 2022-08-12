package com.pettech.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.pettech.data.model.UserPostDetails;
import com.pettech.user.service.PostUserService;

@CrossOrigin
@RestController
@RequestMapping("/user/post")
public class UserPosetController {

	@Autowired
	private PostUserService postUserService;
	
	@PostMapping("/upload/pet/post")
	public ResponseEntity<?> upLoadPet( @RequestPart("petPostDetails") String petPostDetails, @RequestParam("file") MultipartFile file) throws JsonMappingException, JsonProcessingException {
		ObjectMapper pos = new ObjectMapper();
		return postUserService.upLoad(pos.readValue(petPostDetails, UserPostDetails.class), file);
	}
	
//	on profile my post pet list
	@GetMapping("/my/post/pet/list/{id}/{postId}")
	public ResponseEntity<?>myPostDetails(@PathVariable String id, @PathVariable String postId) {
		return postUserService.getPostDetails(id, postId);
	}
}
