package com.tragent.pressing.generator;

import java.util.Random;

import com.tragent.pressing.model.Role;
import org.apache.commons.lang3.RandomStringUtils;

public class RoleGenerator {

	public static Role generateRole() {
		String name = RandomStringUtils.random(6, true, true);
		String description = RandomStringUtils.random(15, true, true);
		Role role = new Role(name, description, null);
		role.setId(new Random().nextLong());
		return role;
	}

	public static String toJSON(Role role) {
		if (role == null) {
			role = RoleGenerator.generateRole();
		}
		return Generator.toJSON(role);
	}

}
