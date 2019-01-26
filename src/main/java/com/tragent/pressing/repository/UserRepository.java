package com.tragent.pressing.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.tragent.pressing.model.CustomUser;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long>{
	
	CustomUser findByUsername(@Param("name") String name);
	Collection<CustomUser> findByIsActive(@Param("isActive") boolean isActive);

}
