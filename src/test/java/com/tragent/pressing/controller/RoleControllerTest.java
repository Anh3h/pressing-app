package com.tragent.pressing.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.tragent.pressing.generator.Generator;
import com.tragent.pressing.generator.PermissionGenerator;
import com.tragent.pressing.generator.RoleGenerator;
import com.tragent.pressing.model.Role;
import com.tragent.pressing.model.RoleDTO;
import com.tragent.pressing.service.PermissionService;
import com.tragent.pressing.service.RoleService;
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
@WebMvcTest(RoleController.class)
public class RoleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RoleService roleService;

	@MockBean
	private PermissionService permissionService;

	@Test
	public void whenCreateRoleRequestIsMade_NewCreatedRoleandHttpStatus201AreReturned() throws Exception {
		RoleDTO roleDTO = RoleGenerator.generateRoleDTO();
		Role role = roleDTO.toRole();
		role.addPermission(PermissionGenerator.generatePermission());

		given(this.roleService.create(any(RoleDTO.class))).willReturn(role);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles")
				.content(Generator.toJSON(roleDTO))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("name").value(role.getName()));
	}

	@Test
	public void whenUpdateRoleRequestIsMade_AnUpdatedRoleIsReturned() throws Exception {
		RoleDTO roleDTO = RoleGenerator.generateRoleDTO();
		Role role = roleDTO.toRole();
		role.addPermission(PermissionGenerator.generatePermission());

		given(this.roleService.update(any(RoleDTO.class))).willReturn(role);

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/roles/" + role.getId())
				.content(Generator.toJSON(roleDTO))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("name").value(role.getName()));
	}

	@Test
	public void whenUpdateRoleRequestIsMadeWithMisMatchIdOnPathAndObject_HttpStatus400IsReturned() throws Exception {
		RoleDTO roleDTO = RoleGenerator.generateRoleDTO();

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/roles/" + 1L)
				.content(Generator.toJSON(roleDTO))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void whenGetRoleByIdRequestIsMade_AnExistingRoleAndHttpStatusOkIsReturned() throws Exception {
		Role role = RoleGenerator.generateRole();

		given(this.roleService.findById(role.getId())).willReturn(role);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/" + role.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value(role.getName()));
	}

	@Test
	public void whenGetRoleByIdRequestIsMadeonNonExistingRole_HttpStatus404IsReturned() throws Exception {
		given(this.roleService.findById(1L)).willReturn(null);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles" + 1L))
				.andExpect(status().isNotFound());
	}

	@Test
	public void whenGetRolesRequestIsMade_AllRolesAndHttpStatusOkAreReturned() throws Exception {
		List<Role> roles = new ArrayList<>();
		roles.add(RoleGenerator.generateRole());
		roles.add(RoleGenerator.generateRole());

		given(this.roleService.findAll()).willReturn(roles);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles"))
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void whenGetRoleByNameIsMade_ARoleIsReturnes() throws Exception {
		Role role = RoleGenerator.generateRole();
		given(this.roleService.findByName(role.getName())).willReturn(role);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/name/" + role.getName()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name", is(role.getName())));
	}

	@Test
	public void whenGetRolePermissionsIsMade_ACollectionOfAllPermissionsForThatRoleIsReturned() throws Exception {
		Role role = RoleGenerator.generateRole();
		given(this.roleService.findById(role.getId())).willReturn(role);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/" + role.getId() + "/permissions"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", is(role.getPermission().get(0).getName())));
	}
}
