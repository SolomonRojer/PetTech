package com.pettech.user.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
	Environment env;
	@Autowired
	PetSaleRepository petSaleRepository;

	@Override
	public ResponseEntity<Optional<userDetails>> getDetails(String id) {
		Optional<userDetails> user = userRepository.findById(id);
		  return ResponseEntity.status(HttpStatus.OK).body(user);
	}

//	return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.PARTIAL_CONTENT.value())
//			.message(env.getProperty("password.does.not.match")).build());

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
	public ResponseEntity<?> upLoad(ProductDetails petDetails, List< MultipartFile> file) {
		 UUID uuid=UUID.randomUUID();
		for(MultipartFile multi : file) {
		ProductDetails obj=new ProductDetails();
		obj.setAddress(petDetails.getAddress());
		obj.setDescription(petDetails.getDescription());
		obj.setPetName(petDetails.getPetName());
		obj.setPetId(uuid.toString());
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
	public ResponseEntity<List<ProductDetails>> getSaleDetails(String id, String petId) {
		Optional<userDetails> user = userRepository.findById(id);

		List<ProductDetails> mySale = petSaleRepository.findByuserId(user.get(),petId);
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
	
		   List<ProductDetails> homePage = (List<ProductDetails>) petSaleRepository.findGroupByPet();
		   return ResponseEntity.status(HttpStatus.OK).body(homePage);
 }



	@Override
	public ResponseEntity<?> upDate(MultipartFile file, String id) throws IOException {
		Optional<userDetails> user = userRepository.findById(id);
		user.get().setProfilePic(file.getBytes());
		userRepository.save(user.get());
		return null;
	}
}



























