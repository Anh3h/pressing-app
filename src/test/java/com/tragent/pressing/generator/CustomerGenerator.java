package com.tragent.pressing.generator;

import java.util.Random;

import com.tragent.pressing.model.Customer;
import org.apache.commons.lang3.RandomStringUtils;

public class CustomerGenerator {

	public static Customer generateCustomer() {
		Long id = new Random().nextLong();
		String firstName = RandomStringUtils.random(7, true, true);
		String lastName = RandomStringUtils.random(7, true, true);
		String email = RandomStringUtils.random(10, true, true).concat("@mail.com");
		String telephone = RandomStringUtils.random(9, false, true);
		Customer customer = new Customer(firstName, lastName, email, telephone, true);
		customer.setId(id);
		return customer;
	}
}
