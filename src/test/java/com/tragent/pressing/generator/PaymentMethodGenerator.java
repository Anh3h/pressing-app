package com.tragent.pressing.generator;

import java.util.Random;

import com.tragent.pressing.model.PaymentMethod;
import org.apache.commons.lang3.RandomStringUtils;

public class PaymentMethodGenerator {

	public static PaymentMethod generatePaymentMethod() {
		Long id = new Random().nextLong();
		String name = RandomStringUtils.random(10, true, true);
		String description = RandomStringUtils.random(20, true, true);
		PaymentMethod paymentMethod = new PaymentMethod(name, description, true);
		paymentMethod.setId(id);
		return paymentMethod;
	}
}
