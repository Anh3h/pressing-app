package com.tragent.pressing.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tragent.pressing.model.CustomUser;
import com.tragent.pressing.model.Role;
import com.tragent.pressing.model.UserDTO;
import com.tragent.pressing.service.RoleService;
import com.tragent.pressing.service.UserService;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RequestMapping("api/v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	/**
	 * Get all uses.
	 * 
	 * @param isActive
	 * @return Collection of users or user with the given username
	 */
	@RequestMapping(method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<CustomUser>> getUsers(@RequestParam(value = "active", required = false) String isActive){
		Collection<CustomUser> users = new ArrayList<>();
		if (isActive != null) {
			if (isActive.compareToIgnoreCase("true") == 0) {
				Collection<CustomUser> activedUsers = userService.findByIsActive(true);
				users.addAll(activedUsers);
			} else if (isActive.compareToIgnoreCase("false") == 0){
				Collection<CustomUser> deActivatedUsers = userService.findByIsActive(false);
				users.addAll(deActivatedUsers);
			}
		} else {
			Collection<CustomUser> allUser = userService.findAll();
			users.addAll(allUser);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);	
	}

	/**
	 * Get user by username.
	 *
	 * @param username
	 * @return User object or 404 if user is not found
	 */
	@RequestMapping(value="/username/{username}",
			method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomUser> getUserById(@PathVariable("username") String username){
		CustomUser user = this.userService.findByUserName(username);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	/**
	 * Get user by user id.
	 * 
	 * @param userId
	 * @return User object or 404 if user is not found
	 */
	@RequestMapping(value="/{id}",
			method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomUser> getUserById(@PathVariable("id") Long userId){
		CustomUser user = this.userService.findById(userId);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	/**
	 * Create new user.
	 * 
	 * @param userDTO
	 * @return User object (created user object)
	 */
	@RequestMapping(method=RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomUser> createUser(@RequestBody UserDTO userDTO){
		CustomUser createdUser = this.userService.create(userDTO);
		if (createdUser == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}
	
	/**
	 * Update user's information.
	 * 
	 * @param userDTO
	 * @return User object (updated user object)
	 */
	@RequestMapping(value="/{userId}",
			method=RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomUser> updateUser(@RequestBody UserDTO userDTO,
			@PathVariable("userId") Long userId){
		if (userDTO.getId().compareTo(userId) == 0) {
			CustomUser updateUser = this.userService.update(userDTO);
			if (updateUser != null) {
				return new ResponseEntity<>(updateUser, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Delete user's information
	 * 
	 * @param userId
	 * @return HTTP status 204, .
	 */
	@RequestMapping(value="/{id}",
			method=RequestMethod.DELETE)
	public ResponseEntity<CustomUser> deactivateUser(@PathVariable("userId") Long userId){
		this.userService.deactivate(userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
