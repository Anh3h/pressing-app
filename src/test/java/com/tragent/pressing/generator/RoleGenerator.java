package com.tragent.pressing.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.tragent.pressing.model.Permission;
import com.tragent.pressing.model.Role;
import com.tragent.pressing.model.RoleDTO;
import org.apache.commons.lang3.RandomStringUtils;

public class RoleGenerator {

	public static Role generateRole() {
		String name = RandomStringUtils.random(6, true, true);
		String description = RandomStringUtils.random(15, true, true);
		List<Permission> permissions = new ArrayList<>();
		permissions.add(PermissionGenerator.generatePermission());
		Role role = new Role(name, description, permissions);
		role.setId(new Random().nextLong());
		return role;
	}

	public static RoleDTO generateRoleDTO() {
		Long id = new Random().nextLong();
		String name = RandomStringUtils.random(6, true, true);
		String description = RandomStringUtils.random(15, true, true);
		return new RoleDTO(id, name, description, 1L);
	}

	public static String toJSON(Role role) {
		if (role == null) {
			role = RoleGenerator.generateRole();
		}
		return Generator.toJSON(role);
	}

}
