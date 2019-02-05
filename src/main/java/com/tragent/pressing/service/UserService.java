package com.tragent.pressing.service;

import java.util.Collection;

import com.tragent.pressing.model.CustomUser;
import com.tragent.pressing.model.UserDTO;

/**
 * Service that provides CRUD operations for users
 **/
public interface UserService {

	/**
	 * Get all users in the system.
	 * 
	 * @return Collection of all users 
	 */
	Collection<CustomUser> findAll();
	
	/**
	 * Find an user by Id.
	 * 
	 * @param id
	 * @return CustomUser object if found, else return null
	 */
	CustomUser findById(Long id);
	
	/**
	 * Find an user by username.
	 * 
	 * @param username
	 * @return CustomUser object if found, else return null
	 */
	CustomUser findByUserName(String username);
	
	/**
	 * Find users with active accounts.
	 * 
	 * @param isActive
	 * @return Collection of users
	 */
	Collection<CustomUser> findByIsActive(boolean isActive);
	
	/**
	 * Create new user.
	 * 
	 * @param user
	 * @return CustomUser object (Created user object)
	 */
	CustomUser create(UserDTO user);
	
	/**
	 * Update an existing user's information.
	 * 
	 * @param user
	 * @return CustomUser object (Updated user object)
	 */
	CustomUser update(UserDTO user);
	
	/**
	 * Deactivate a user's account.
	 * 
	 * @param id
	 */
	void deactivate(Long id);
	
}
