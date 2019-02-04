package com.tragent.pressing.generator;

import java.util.Random;

import com.tragent.pressing.model.Category;
import com.tragent.pressing.model.Item;
import org.apache.commons.lang3.RandomStringUtils;

public class ItemGenerator {

	public static Item generateItem() {
		Long id = new Random().nextLong();
		String name = RandomStringUtils.random(7, true, true);
		String description = RandomStringUtils.random(15, true, true);
		Double cost = new Random().nextDouble();
		Category category = CategoryGenerator.generateCategory();
		Item item = new Item(name, description, cost, category);
		item.setId(id);
		return item;
	}
}
