package com.tragent.pressing.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tragent.pressing.generator.PermissionGenerator;
import com.tragent.pressing.generator.RoleGenerator;
import com.tragent.pressing.model.Role;
import com.tragent.pressing.model.RoleDTO;
import com.tragent.pressing.repository.PermissionRepository;
import com.tragent.pressing.repository.RoleRepository;
import com.tragent.pressing.service.implementation.RoleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class RoleServiceTest {

	@TestConfiguration
	static class RoleServiceTestConfiguration{
		@Bean
		public RoleService roleService() {
			return new RoleServiceImpl();
		}
	}

	@Autowired
	private RoleService roleService;

	@MockBean
	private PermissionRepository permissionRepository;

	@MockBean
	private RoleRepository roleRepository;

	@Test
	public void createRole_shouldReturnNewlyCreatedRole() {
		RoleDTO roleDTO = RoleGenerator.generateRoleDTO();
		Role role = roleDTO.toRole();
		role.addPermission(PermissionGenerator.generatePermission());
		given(this.roleRepository.save(any(Role.class))).willReturn(role);
		given(this.roleRepository.findByName(any(String.class))).willReturn(null);
		given(this.permissionRepository.findOne(any(Long.class)))
				.willReturn(role.getPermission().get(0));

		Role newRole = this.roleService.create(roleDTO);

		assertThat(newRole).isEqualTo(role);
	}

	@Test
	public void updateRole_shouldReturnUpdatedRole() {
		RoleDTO roleDTO = RoleGenerator.generateRoleDTO();
		Role role = roleDTO.toRole();
		role.addPermission(PermissionGenerator.generatePermission());
		given(this.roleRepository.save(any(Role.class))).willReturn(role);
		given(this.roleRepository.findByName(any(String.class))).willReturn(role);
		given(this.permissionRepository.findOne(any(Long.class)))
				.willReturn(role.getPermission().get(0));

		Role updatedRole = this.roleService.update(roleDTO);

		assertThat(updatedRole).isEqualTo(role);
	}

	@Test
	public void getRoleById_shouldReturnExistingRoleWithThatId() {
		Role role = RoleGenerator.generateRole();
		given(this.roleRepository.findOne(role.getId())).willReturn(role);

		Role gottenRole = this.roleService.findById(role.getId());

		assertThat(gottenRole).isEqualTo(role);
	}

	@Test
	public void getRoleByName_shouldReturnExistingRoleWithThatName() {
		Role role = RoleGenerator.generateRole();
		given(this.roleRepository.findByName(role.getName())).willReturn(role);

		Role gottenRole = this.roleService.findByName(role.getName());

		assertThat(gottenRole).isEqualTo(role);
	}

	@Test
	public void getRoles_shouldReturnACollectionsOfAllRoles() {
		List<Role> roles = new ArrayList<>();
		roles.add(RoleGenerator.generateRole());
		roles.add(RoleGenerator.generateRole());
		given(this.roleRepository.findAll()).willReturn(roles);

		Collection<Role> gottenRoles = this.roleService.findAll();

		assertThat(gottenRoles).isEqualTo(roles);
	}
}
