package com.tragent.pressing.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tragent.pressing.generator.UserGenerator;
import com.tragent.pressing.model.CustomUser;
import com.tragent.pressing.repository.UserRepository;
import com.tragent.pressing.service.implementation.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserServiceTest {

	@TestConfiguration
	static class UserServiceTestConfiguration {

		@Bean
		public static UserService userService() {
			return new UserServiceImpl();
		}
	}

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@Test
	public void createUser_shouldReturnNewlyCreatedUser() {
		CustomUser user = UserGenerator.generateUser();
		given( this.userRepository.save(user) ).willReturn(user);
		given(  this.userRepository.findByUsername(user.getUsername()) ).willReturn(null);
		given( this.passwordEncoder.encode(user.getPassword()) ).willReturn(user.getPassword());

		CustomUser newUser = this.userService.create(user);

		assertThat(newUser).isEqualTo(user);
	}

	@Test
	public void updateUser_shouldReturAnUpdateUser() {
		CustomUser user = UserGenerator.generateUser();
		given( this.userRepository.save(user) ).willReturn(user);
		given( this.passwordEncoder.encode(user.getPassword()) ).willReturn(user.getPassword());

		CustomUser updatedUser = this.userService.update(user);

		assertThat(updatedUser).isEqualTo(user);
	}

	@Test
	public void getUserById_ShouldReturnAUserAccountIfAvailable() {
		CustomUser user = UserGenerator.generateUser();
		given( this.userRepository.findOne(user.getId()) ).willReturn(user);

		CustomUser gottenUser = this.userService.findById(user.getId());

		assertThat(gottenUser).isEqualTo(user);
	}

	@Test
	public void getUserByUsername_ShouldReturnAAUserAccountIfAvailable() {
		CustomUser user = UserGenerator.generateUser();
		given( this.userRepository.findByUsername(user.getUsername()) ).willReturn(user);

		CustomUser gottenUser = this.userService.findByUserName(user.getUsername());

		assertThat(gottenUser).isEqualTo(user);
	}

	@Test
	public void getActiveUsers_ShouldReturnActiveUsers() {
		List<CustomUser> users = new ArrayList<>();
		users.add(UserGenerator.generateUser());
		users.add(UserGenerator.generateUser());
		given( this.userRepository.findByIsActive(true) ).willReturn(users);

		Collection<CustomUser> activeUsers = this.userService.findByIsActive(true);

		assertThat(activeUsers).isEqualTo(users);
	}

	@Test
	public void getAllUsers_ShouldReturnAllUsersInDB() {
		List<CustomUser> users = new ArrayList<>();
		CustomUser tempUser = UserGenerator.generateUser();
		tempUser.setActive(false);
		users.add(tempUser);
		users.add(UserGenerator.generateUser());
		given( this.userRepository.findAll() ).willReturn(users);

		Collection<CustomUser> gottenUsers = this.userService.findAll();

		assertThat(gottenUsers).isEqualTo(users);
	}
}
