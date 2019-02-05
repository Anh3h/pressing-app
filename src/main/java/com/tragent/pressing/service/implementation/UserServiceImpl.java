package com.tragent.pressing.service.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.tragent.pressing.model.Role;
import com.tragent.pressing.model.UserDTO;
import com.tragent.pressing.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tragent.pressing.model.CustomUser;
import com.tragent.pressing.repository.UserRepository;
import com.tragent.pressing.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	@Secured("ROLE_ADMINISTRATION")
	public Collection<CustomUser> findAll() {
		
		List<CustomUser> users = userRepository.findAll();
		return users;
	}

	@Override
	public CustomUser findById(Long id) {
		
		CustomUser user = userRepository.findOne(id);
		return user;
	}

	@Override
	public CustomUser findByUserName(String username) {
		CustomUser user = userRepository.findByUsername(username);
		return user;
	}

	@Override
	public Collection<CustomUser> findByIsActive(boolean isActive) {
		Collection<CustomUser> users = userRepository.findByIsActive(isActive);
		return users;
	}

	@Override
	@Secured("ROLE_ADMINISTRATION")
	public CustomUser create(UserDTO userDTO) {
		if (findByUserName(userDTO.getUsername()) == null) {
			CustomUser user = this.convertUserDTOtoCustomUser(userDTO);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			return userRepository.save(user);
		}
		return null;
	}

	@Override
	public CustomUser update(UserDTO userDTO) {
		CustomUser user = this.userRepository.findOne(userDTO.getId());
		if (user != null) {
			userDTO.setPassword(user.getPassword());
			userDTO.setActive(true);
			user = this.convertUserDTOtoCustomUser(userDTO);
			return userRepository.save(user);
		}
		return null;
	}

	@Override
	@Secured("ROLE_ADMINISTRATION")
	public void deactivate(Long id) {
		
		CustomUser user = findById(id);
		user.setActive(false);
	}

	private CustomUser convertUserDTOtoCustomUser(UserDTO userDTO) {
		CustomUser user = userDTO.toUser();
		List<Role> roles = userDTO.getRoleIds()
				.stream()
				.map( roleId ->  this.roleRepository.findOne(roleId))
				.collect(Collectors.toList());
		user.setRoles(roles);
		return user;
	}
}
