package com.tragent.pressing.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.tragent.pressing.generator.Generator;
import com.tragent.pressing.generator.UserGenerator;
import com.tragent.pressing.model.CustomUser;
import com.tragent.pressing.model.UserDTO;
import com.tragent.pressing.service.RoleService;
import com.tragent.pressing.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private RoleService roleService;

	@Test
	public void whenAllUsersAreRequested_ACollectionOfUsersIsReturned() throws Exception {
		List<CustomUser> users = new ArrayList<>();
		users.add(UserGenerator.generateUser());
		users.add(UserGenerator.generateUser());

		given(this.userService.findAll()).willReturn(users);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void whenAUserIsRequestedByUsername_ASingletonOfUserIsReturned() throws Exception {
		CustomUser user = UserGenerator.generateUser();

		given(this.userService.findByUserName(user.getUsername())).willReturn(user);

		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/v1/users/username/" + user.getUsername()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("firstName", is(user.getFirstName())));
	}

	@Test
	public void whenAUserIsRequestedById_AUserIsReturned() throws Exception {
		CustomUser user = UserGenerator.generateUser();

		given(this.userService.findById(user.getId())).willReturn(user);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/" + user.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("firstName", is(user.getFirstName())));
	}

	@Test
	public void whenANonExistingUserIsRequestedById_HttpStatus404IsReturned() throws Exception {
		given(this.userService.findById(1L)).willReturn(null);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/" + 1L))
				.andExpect(status().isNotFound());
	}

	@Test
	public void whenAUserCreateRequestIsSent_ACreatedUserIsReturned() throws Exception {
		UserDTO userDTO = UserGenerator.generateUserDTO();
		CustomUser user = userDTO.toUser();

		given(this.userService.create(any(UserDTO.class))).willReturn(user);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
				.content(Generator.toJSON(userDTO))
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("firstName", is(user.getFirstName())));
	}

	@Test
	public void whenAUserUpdateRequestIsSent_AnUpdatedUserIsReturned() throws Exception {
		UserDTO userDTO = UserGenerator.generateUserDTO();
		CustomUser user = userDTO.toUser();

		given(this.userService.update(any(UserDTO.class))).willReturn(user);

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/" + userDTO.getId())
				.content(Generator.toJSON(userDTO))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("firstName", is(user.getFirstName())));
	}

	@Test
	public void WhenANonExistingUserIsUpdated_HttpStatus400IsReturned() throws Exception {
		UserDTO userDTO = UserGenerator.generateUserDTO();

		given(this.userService.update(any(UserDTO.class))).willReturn(null);

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/" + userDTO.getId())
				.content(Generator.toJSON(userDTO))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void WhenANonExistingUserIsUpdated_HttpStatusBadRequestIsReturned() throws Exception {
		CustomUser user = UserGenerator.generateUser();
		UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(),
				user.isActive(), user.getRoles().get(0).getId(), user.getTelephone());

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/" + 1L)
				.content(Generator.toJSON(userDTO))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}
}
