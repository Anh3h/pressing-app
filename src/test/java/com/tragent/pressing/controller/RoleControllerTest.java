package com.tragent.pressing.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.tragent.pressing.generator.Generator;
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
		Role role = RoleGenerator.generateRole();
		List<Long> permissionIds = new ArrayList<>();
		permissionIds.add(role.getPermission().get(0).getId());
		RoleDTO roleDTO = new RoleDTO(role.getId(), role.getName(), role.getDescription(), null);

		given(this.roleService.create(any(Role.class))).willReturn(role);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles")
				.content(Generator.toJSON(roleDTO))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("name").value(role.getName()));
	}

}
