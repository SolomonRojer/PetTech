package com.pettech.data.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.pettech.data.model.userDetails;

@Repository
public interface UserRepository extends CrudRepository<userDetails, String> {
//	@Query(value = "SELECT * from user_details where email= :email", nativeQuery = true)
//	userDetails findByEmail(@Param("email")String email);


	Optional<userDetails> findByuserName(String username);

	userDetails findByEmail(String email);






}
