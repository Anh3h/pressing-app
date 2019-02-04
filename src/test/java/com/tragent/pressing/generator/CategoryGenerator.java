package com.tragent.pressing.generator;

import java.util.Random;

import com.tragent.pressing.model.Category;
import org.apache.commons.lang3.RandomStringUtils;

public class CategoryGenerator {

	public static Category generateCategory()  {
		Long id = new Random().nextLong();
		String name = RandomStringUtils.random(7, true, true);
		String description = RandomStringUtils.random(15, true, true);
		Category category = new Category(name, description);
		category.setId(id);
		return category;
	}
}
