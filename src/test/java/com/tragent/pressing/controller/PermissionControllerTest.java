package com.tragent.pressing.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.tragent.pressing.generator.Generator;
import com.tragent.pressing.generator.PermissionGenerator;
import com.tragent.pressing.model.Permission;
import com.tragent.pressing.service.PermissionService;
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
@WebMvcTest(PermissionController.class)
public class PermissionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PermissionService permissionService;

	@Test
	public void whenACreatePermissionRequestIsMade_thenACreatedPermissionIsReturned() throws Exception {
		Permission permission = PermissionGenerator.generatePermission();
		given(this.permissionService.create(any(Permission.class))).willReturn(permission);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/permissions")
				.content(Generator.toJSON(permission))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("name").value(permission.getName()));
	}

	@Test
	public void whenAnUpdateRequestIsMade_thenAnUpdatedPermissionIsReturned() throws Exception {
		Permission permission = PermissionGenerator.generatePermission();
		given(this.permissionService.update(any(Permission.class))).willReturn(permission);

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/permissions/" + permission.getId())
				.content(Generator.toJSON(permission))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("name").value(permission.getName()));
	}

	@Test
	public void whenAnUpdateRequestIsMadeWithMisMatchPathAndObjectId_thenABadRequestErrorIsReturned() throws Exception {
		Permission permission = PermissionGenerator.generatePermission();

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/permissions/" + 1L)
				.content(Generator.toJSON(permission))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void whenAGetByIdRequestIsMade_thenPermissionForThatIdIsReturened() throws Exception {
		Permission permission = PermissionGenerator.generatePermission();
		given(this.permissionService.findById(permission.getId())).willReturn(permission);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/permissions/" + permission.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value(permission.getName()));
	}

	@Test
	public void whenGetByIdRequestIsMadeOnNonExistingPermission_thenNotFoundErrorIsReturned() throws Exception {
		given(this.permissionService.findById(any(Long.class))).willReturn(null);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/permissions/" + 1L))
				.andExpect(status().isNotFound());
	}

	@Test
	public void whenGetPermissionsRequestIsMade_thenACollectionOfPermissionsIsReturned() throws Exception {
		List<Permission> permissions = new ArrayList<>();
		permissions.add(PermissionGenerator.generatePermission());
		permissions.add(PermissionGenerator.generatePermission());
		given(this.permissionService.findAll()).willReturn(permissions);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/permissions"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}
}
