package com.pettech.user.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pettech.data.model.UserPostDetails;

@Service
public interface PostUserService {

	public ResponseEntity<?> upLoad(UserPostDetails readValue, MultipartFile file);

	public ResponseEntity<?> getPostDetails(String id, String postId);

	
}
