package com.pettech.user.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pettech.data.model.ProductDetails;
import com.pettech.data.model.userDetails;
import com.pettech.data.repo.PetSaleRepository;
import com.pettech.data.repo.UserRepository;
import com.pettech.data.response.MessageResponse;
import com.pettech.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PetSaleRepository petSaleRepository;

	@Override
	public ResponseEntity<Optional<userDetails>> getDetails(String id) {
		Optional<userDetails> user = userRepository.findById(id);
		  return ResponseEntity.status(HttpStatus.OK).body(user);
	}



	@Override
	public ResponseEntity<?> upDateUser(MultipartFile file, String fileDescription) {
		Optional<userDetails> user = userRepository.findById(fileDescription);
		try {
			user.get().setProfilePic(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userRepository.save(user.get());
		return null;
	}



	@Override
	public ResponseEntity<?> upDate(userDetails userDetails) {
		Optional<userDetails> user = userRepository.findById(userDetails.getId());
		userDetails.setProfilePic(user.get().getProfilePic());
		userRepository.save(userDetails);
		return null;
	}


	
	@Override
	public ResponseEntity<?> upLoad(ProductDetails petDetails, List< MultipartFile> file) {
		for(MultipartFile multi : file) {
			System.out.println(petDetails);
		ProductDetails obj=new ProductDetails();
		obj.setAddress(petDetails.getAddress());
		obj.setDescription(petDetails.getDescription());
		obj.setPetName(petDetails.getPetName());
		try {
			obj.setPetPhotos(multi.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		obj.setPrice(petDetails.getPrice());
		obj.setUserId(petDetails.getUserId());
		petSaleRepository.save(obj);
		System.out.println(petDetails);
		}
		return null;
	}

	@Override
	public ResponseEntity<List<ProductDetails>> getSaleDetails(String id) {
		Optional<userDetails> user = userRepository.findById(id);

		List<ProductDetails> mySale = petSaleRepository.findByuserId(user.get());
		  return ResponseEntity.status(HttpStatus.OK).body(mySale);
	}

//	@Override
//	public ResponseEntity<?> listDesignation() {
//		try {
//			List<Designation> list = designationRepo.findAll();
//			return ResponseEntity.ok(MessageResponse.builder().message(env.getProperty("designation.fetched"))
//					.status(HttpStatus.OK.value()).response(list).build());
//		} catch (Exception e) {
//			LogRequest info=LogRequest.builder().apiName("admin/list/designation").logInfo(e.getLocalizedMessage().toString()).statusCode("400").createdTime(now.toString()).build();
//			loginfo.saveLogInformation(info);
//			return ResponseEntity.ok(PaginationListResponse.builder().status(HttpStatus.BAD_REQUEST.value())
//					.message(env.getProperty("Problem.designation")).build());
//		}
// return ResponseEntity.status(HttpStatus.OK).body(user);
//	}

	@Override
	public ResponseEntity<?> listHomePost() {
	
		   List<ProductDetails> homePage = (List<ProductDetails>) petSaleRepository.findAll();
		   return ResponseEntity.status(HttpStatus.OK).body(homePage);
 }
}


























