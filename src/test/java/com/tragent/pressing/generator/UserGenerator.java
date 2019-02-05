package com.tragent.pressing.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.tragent.pressing.model.CustomUser;
import com.tragent.pressing.model.Role;
import com.tragent.pressing.model.UserDTO;
import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {

	public static CustomUser generateUser() {
		Long id = new Random().nextLong();
		String firstName = RandomStringUtils.random(6, true,true);
		String lastName = RandomStringUtils.random(6, true,true);
		String username = RandomStringUtils.random(6, true,true);
		String password = RandomStringUtils.random(6, true,true);
		String telephone = RandomStringUtils.random(9, false, true);
		List<Role> roles = new ArrayList<>();
		roles.add(RoleGenerator.generateRole());

		return new CustomUser(id, firstName, lastName, username, password, true, roles, telephone);
	}

	public static UserDTO generateUserDTO() {
		Long id = new Random().nextLong();
		String firstName = RandomStringUtils.random(6, true,true);
		String lastName = RandomStringUtils.random(6, true,true);
		String username = RandomStringUtils.random(6, true,true);
		String password = RandomStringUtils.random(6, true,true);
		String telephone = RandomStringUtils.random(9, false, true);

		return new UserDTO(id, firstName, lastName, username, password, true, 1L, telephone);
	}

	public static String toJSON(CustomUser user) {
		if (user == null) {
			user = generateUser();
		}
		return Generator.toJSON(user);
	}

}
