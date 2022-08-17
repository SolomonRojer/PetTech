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

import com.pettech.data.model.UserPostDetails;
import com.pettech.data.model.userDetails;
import com.pettech.data.repo.PetPostRepository;
import com.pettech.data.repo.UserPetPostRepository;
import com.pettech.data.repo.UserRepository;
import com.pettech.data.response.MessageResponse;
import com.pettech.user.service.PostUserService;

@Service
public class UserPostServiceImpl implements PostUserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	Environment env;
	@Autowired
	PetPostRepository petPostRepository;
	@Autowired
	UserPetPostRepository userPetPostRepository;

	@Override
	public ResponseEntity<?> upLoad(UserPostDetails readValue, MultipartFile file) {
		try {
			UUID uuid = UUID.randomUUID();

			UserPostDetails post = new UserPostDetails();
			post.setDescription(readValue.getDescription());
			post.setLocation(readValue.getLocation());
			post.setTitle(readValue.getTitle());
			post.setHashtag(readValue.getHashtag());
			post.setPostId(uuid.toString());
			try {
				post.setPetPhoto(file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			post.setUserId(readValue.getUserId());
			petPostRepository.save(post);
			return ResponseEntity.ok(MessageResponse.builder().data(post).status(HttpStatus.OK.value())
					.message(env.getProperty("sale.pet.posted.successfully")).build());

		} catch (Exception e) {
			return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.BAD_REQUEST.value())
					.message(env.getProperty("my.sale.list.faild")).build());
		}
	}

//	@GetMapping("/my/post/pet/list/{id}/{postId}") on profile my post pet list
	@Override
	public ResponseEntity<MessageResponse> getPostDetails(String id, String postId) {
		try {
			Optional<userDetails> user = userRepository.findById(id);
			List<UserPostDetails> myPost = userPetPostRepository.findByuserId(user.get(), postId);

			return ResponseEntity.ok(MessageResponse.builder().data(myPost).status(HttpStatus.OK.value())
					.message(env.getProperty("my.posted.pet.list.successfully")).build());
		} catch (Exception e) {
			return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.BAD_REQUEST.value())
					.message(env.getProperty("my.posted.pet.list.faild")).build());
		}
	}

//	@GetMapping("/pet/list") all pet post list on home page
	@Override
	public ResponseEntity<?> listHomePetPost() {
		try {
			List<UserPostDetails> homePage = (List<UserPostDetails>) userPetPostRepository.findGroupByPet();
			return ResponseEntity.ok(MessageResponse.builder().data(homePage).status(HttpStatus.OK.value())
					.message(env.getProperty("post.list.home.page.successfully")).build());

		} catch (Exception e) {
			return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.BAD_REQUEST.value())
					.message(env.getProperty("post.list.home.page.faild")).build());
		}
	}

//	@DeleteMapping(value = "/delete/my/pet/{id}" detele my pet post
	@Override
	public ResponseEntity<MessageResponse> deleteMyPet(String id) {
		try {

			UserPostDetails result = userPetPostRepository.findByPetId(id);
			userPetPostRepository.delete(result);
			return ResponseEntity.ok(MessageResponse.builder().data(result).status(HttpStatus.OK.value())
					.message(env.getProperty("my.sale.list.successfully")).build());

		} catch (Exception e) {
			return ResponseEntity.ok(MessageResponse.builder().status(HttpStatus.BAD_REQUEST.value())
					.message(env.getProperty("my.sale.list.faild")).build());
		}
	}
}