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

//		@GetMapping("/profile/details/{id}")
	@Override
	public ResponseEntity<MessageResponse> getDetails(String id) {
		try {
			Optional<userDetails> user = userRepository.findById(id);
			return ResponseEntity.ok(MessageResponse.builder().data(user.get()).status(HttpStatus.OK.value()).build());
		} catch (Exception e) {
			return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.BAD_REQUEST.value())
					.message(env.getProperty("update.user.faild")).build());
		}
	}

//  		@PostMapping("/profile/photo/update")
	@Override
	public ResponseEntity<?> upDateUser(MultipartFile file, userDetails fileDescription, String id) {

		try {
			Optional<userDetails> user = userRepository.findById(id);
			try {
				if (!file.isEmpty()) {
					user.get().setProfilePic(file.getBytes());
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
			user.get().setEmail(fileDescription.getEmail());
			user.get().setNumber(fileDescription.getNumber());
			user.get().setDiscription(fileDescription.getDiscription());
			user.get().setAddress(fileDescription.getAddress());
			user.get().setName(fileDescription.getName());
			userRepository.save(user.get());

			return ResponseEntity.ok(MessageResponse.builder().data(user).status(HttpStatus.OK.value())
					.message(env.getProperty("update.user.successfully")).build());
		} catch (Exception e) {
			return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.BAD_REQUEST.value())
					.message(env.getProperty("update.user.faild")).build());
		}
	}

//   	@PostMapping("/add/doc") for profile photo
	@Override
	public ResponseEntity<?> upDate(MultipartFile file, String id) throws IOException {
		try {
			Optional<userDetails> user = userRepository.findById(id);
			user.get().setProfilePic(file.getBytes());
			userRepository.save(user.get());

			return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.OK.value())
					.message(env.getProperty("user.add.profile.photo.successfully")).build());
		} catch (Exception e) {
			return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.BAD_REQUEST.value())
					.message(env.getProperty("user.add.profile.photo.faild")).build());
		}
	}

//	@PostMapping("/sale/pet")
	@Override
	public ResponseEntity<?> upLoad(ProductDetails petDetails, List<MultipartFile> file) {
		try {
			UUID uuid = UUID.randomUUID();
			for (MultipartFile multi : file) {
				ProductDetails obj = new ProductDetails();
				obj.setAddress(petDetails.getAddress());
				obj.setDescription(petDetails.getDescription());
				obj.setPetName(petDetails.getPetName());
				obj.setPetId(uuid.toString());
				try {
					obj.setPetPhotos(multi.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				obj.setPrice(petDetails.getPrice());
				obj.setUserId(petDetails.getUserId());
				petSaleRepository.save(obj);
			}
			return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.OK.value())
					.message(env.getProperty("sale.pet.posted.successfully")).build());
		} catch (Exception e) {
			return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.BAD_REQUEST.value())
					.message(env.getProperty("sale.pet.posted.faild")).build());
		}

	}

//	@GetMapping("/my/sale/details/{id}/{petId}")
	@Override
	public ResponseEntity<MessageResponse> getSaleDetails(String id, String petId) {
		try {
			Optional<userDetails> user = userRepository.findById(id);
			List<ProductDetails> mySale = petSaleRepository.findByuserId(user.get(), petId);

			return ResponseEntity.ok(MessageResponse.builder().data(mySale).status(HttpStatus.OK.value())
					.message(env.getProperty("my.sale.list.successfully")).build());
		} catch (Exception e) {
			return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.BAD_REQUEST.value())
					.message(env.getProperty("my.sale.list.faild")).build());
		}
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

//	@GetMapping("/sale/post/list")
	@Override
	public ResponseEntity<?> listHomePost() {
		try {
			List<ProductDetails> homePage = (List<ProductDetails>) petSaleRepository.findGroupByPet();
			return ResponseEntity.ok(MessageResponse.builder().data(homePage).status(HttpStatus.OK.value())
					.message(env.getProperty("my.sale.list.successfully")).build());

		} catch (Exception e) {
			return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.BAD_REQUEST.value())
					.message(env.getProperty("my.sale.list.faild")).build());
		}
	}
}
