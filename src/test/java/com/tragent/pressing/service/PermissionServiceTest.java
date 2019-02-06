package com.tragent.pressing.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tragent.pressing.generator.PermissionGenerator;
import com.tragent.pressing.model.Permission;
import com.tragent.pressing.repository.PermissionRepository;
import com.tragent.pressing.service.implementation.PermissionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PermissionServiceTest {

	@TestConfiguration
	static class PermissionServiceTestConfiguration {

		@Bean
		public PermissionService permissionService() {
			return new PermissionServiceImpl();
		}
	}

	@Autowired
	private PermissionService permissionService;

	@MockBean
	private PermissionRepository permissionRepository;

	@Test
	public void createPermission_shouldReturnsCreatedPermission() {
		Permission permission = PermissionGenerator.generatePermission();
		given(this.permissionRepository.save(any(Permission.class))).willReturn(permission);

		Permission newPermission = this.permissionService.create(permission);

		assertThat(newPermission).isEqualTo(permission);
	}

	@Test
	public void updatePermission_shouldReturnUpdatedPermission() {
		Permission permission = PermissionGenerator.generatePermission();
		given(this.permissionRepository.save(any(Permission.class))).willReturn(permission);
		given(this.permissionRepository.findOne(any(Long.class))).willReturn(permission);

		Permission updatedPermission = this.permissionService.update(permission);

		assertThat(updatedPermission).isEqualTo(permission);
	}

	@Test
	public void getPermissionById_shouldPermissionWithThatId() {
		Permission permission = PermissionGenerator.generatePermission();
		given(this.permissionRepository.findOne(permission.getId())).willReturn(permission);

		Permission gottenPermission = this.permissionService.findById(permission.getId());

		assertThat(gottenPermission).isEqualTo(permission);
	}

	@Test
	public void getPermissionByName_shouldReturnPermissionWithThatName() {
		Permission permission = PermissionGenerator.generatePermission();
		given(this.permissionRepository.findByName(permission.getName())).willReturn(permission);

		Permission gottenPermission = this.permissionService.findByName(permission.getName());

		assertThat(gottenPermission).isEqualTo(permission);
	}

	@Test
	public void getAllPermission_shouldReturnACollectionOfAllPermissions() {
		List<Permission> permissions = new ArrayList<>();
		permissions.add(PermissionGenerator.generatePermission());
		permissions.add(PermissionGenerator.generatePermission());
		given(this.permissionRepository.findAll()).willReturn(permissions);

		Collection<Permission> gottenPermissions = this.permissionService.findAll();

		assertThat(gottenPermissions).isEqualTo(permissions);
	}
}
