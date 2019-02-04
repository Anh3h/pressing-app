package com.tragent.pressing.generator;

import java.util.Date;
import java.util.Random;

import com.tragent.pressing.model.Customer;
import com.tragent.pressing.model.Item;
import com.tragent.pressing.model.Status;
import com.tragent.pressing.model.Transaction;

public class TransactionGenerator {

	public static Transaction generateTransaction() {
		Long id = new Random().nextLong();
		Customer customer = CustomerGenerator.generateCustomer();
		Item item = ItemGenerator.generateItem();
		int qty = new Random().nextInt();
		Status status = Status.PENDING;
		Date depositeDate = new Date();
		Date dueDate = new Date();
		Transaction transaction = new Transaction(customer, item, qty, status, "", depositeDate, dueDate);
		transaction.setId(id);
		return transaction;
	}

}
