package com.pettech.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pettech.data.model.ProductDetails;
import com.pettech.data.model.userDetails;

@Repository
public interface PetSaleRepository extends CrudRepository<ProductDetails, String> {

	@Query(value = "select * from product_details where user_details_id= :id", nativeQuery = true)
	List<ProductDetails> findByuserId(@Param("id")userDetails id);

}
//select * from petstore.product_details where user_details_id ="402882e082482ead0182483281870000";