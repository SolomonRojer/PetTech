package com.pettech.user.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pettech.data.model.ProductDetails;
import com.pettech.data.model.userDetails;
import com.pettech.data.response.MessageResponse;

@Service
public interface UserService {


	public ResponseEntity<MessageResponse> getDetails(String id);

	public ResponseEntity<?> upDateUser(MultipartFile file, userDetails fileDescription,String id);


	public ResponseEntity<?> upLoad(ProductDetails petDetails, List< MultipartFile> file);
	
	public ResponseEntity<MessageResponse> getSaleDetails(String id,String petId);

	public ResponseEntity<?> listHomePost();

//	public ResponseEntity<?> upDate(userDetails userDetails, MultipartFile file, String id) throws IOException;

	public ResponseEntity<?> upDate(MultipartFile file, String id) throws IOException;


}
