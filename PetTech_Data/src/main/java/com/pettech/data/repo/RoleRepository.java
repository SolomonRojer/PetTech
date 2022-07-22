package com.pettech.data.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pettech.data.model.ERole;
import com.pettech.data.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
//	Optional<Role> findByName(ERole roleAdmin);
	
	@Query(value="select p from Role p where p.name='ROLE_ADMIN' OR p.name='ROLE_MANAGER'" )
	Role findByName(ERole roleEmployee);

}