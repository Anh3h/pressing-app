package com.tragent.pressing.generator;

import java.util.Random;

import com.tragent.pressing.model.Permission;
import org.apache.commons.lang3.RandomStringUtils;

public class PermissionGenerator {

	public static Permission generatePermission() {
		Long id = new Random().nextLong();
		String name = RandomStringUtils.random(7, true, true);
		String description = RandomStringUtils.random(15, true, false);

		return new Permission(id, name, description);
	}

}
