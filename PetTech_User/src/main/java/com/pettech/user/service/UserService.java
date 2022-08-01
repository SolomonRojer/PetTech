package com.pettech.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pettech.data.model.ProductDetails;
import com.pettech.data.model.userDetails;

@Service
public interface UserService {


	public ResponseEntity<Optional<userDetails>> getDetails(String id);

	public ResponseEntity<?> upDateUser(MultipartFile file, String fileDescription);

	public ResponseEntity<?> upDate(userDetails userDetails);

	public ResponseEntity<?> upLoad(ProductDetails petDetails, List< MultipartFile> file);
	
	public ResponseEntity<List<ProductDetails>> getSaleDetails(String id);

	public ResponseEntity<?> listHomePost();

}
