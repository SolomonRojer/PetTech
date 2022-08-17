package com.pettech.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pettech.data.model.UserPostDetails;
import com.pettech.data.model.userDetails;

@Repository
public interface UserPetPostRepository extends CrudRepository<UserPostDetails, String> {

	@Query(value = "select * from user_post_details where user_details_id= :id AND post_id=:postId", nativeQuery = true)
	List<UserPostDetails> findByuserId(@Param("id") userDetails id, String postId);

	@Query(value = "select * from user_post_details GROUP BY post_id", nativeQuery = true)
	List<UserPostDetails> findGroupByPet();

	@Query(value = "select * from user_post_details where post_id= :id", nativeQuery = true)
	UserPostDetails findByPetId(String id);
}
