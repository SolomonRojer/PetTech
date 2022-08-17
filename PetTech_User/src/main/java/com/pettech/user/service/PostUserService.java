package com.pettech.user.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pettech.data.model.UserPostDetails;
import com.pettech.data.response.MessageResponse;

@Service
public interface PostUserService {

	public ResponseEntity<?> upLoad(UserPostDetails readValue, List<MultipartFile> file);

	public ResponseEntity<?> getPostDetails(String id, String postId);

	public ResponseEntity<?> listHomePetPost();

	public ResponseEntity<MessageResponse> deleteMyPet(String id);

}
