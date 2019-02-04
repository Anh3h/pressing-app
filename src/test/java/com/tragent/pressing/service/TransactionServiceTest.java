package com.tragent.pressing.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tragent.pressing.generator.TransactionGenerator;
import com.tragent.pressing.model.Transaction;
import com.tragent.pressing.repository.TransactionRepository;
import com.tragent.pressing.service.implementation.TransactionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {

	@TestConfiguration
	static class TransactionServiceTestConfiguration {
		@Bean
		public TransactionService transactionService() {
			return new TransactionServiceImpl();
		}
	}

	@Autowired
	private TransactionService transactionService;

	@MockBean
	private TransactionRepository transactionRepository;

	@Test
	public void createTransaction_shouldReturnCreatedTransaction() {
		Transaction transaction = TransactionGenerator.generateTransaction();
		given(this.transactionRepository.save(transaction)).willReturn(transaction);

		Transaction newTransaction = this.transactionService.create(transaction);

		assertThat(newTransaction).isEqualTo(transaction);
	}

	@Test
	public void updateTransaction_shouldReturnedUpdatedTransaction() {
		Transaction transaction = TransactionGenerator.generateTransaction();
		given(this.transactionRepository.save(transaction)).willReturn(transaction);

		Transaction updatedTransaction = this.transactionService.update(transaction);

		assertThat(updatedTransaction).isEqualTo(transaction);
	}

	@Test
	public void getTransactionById_shouldReturnGottenTransction() {
		Transaction transaction = TransactionGenerator.generateTransaction();
		given(this.transactionRepository.findOne(transaction.getId())).willReturn(transaction);

		Transaction gottenTransaction = this.transactionService.findById(transaction.getId());

		assertThat(gottenTransaction).isEqualTo(transaction);
	}

	@Test
	public void getTransactionByCustomerId_shouldReturnGottenTransction() {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(TransactionGenerator.generateTransaction());
		given(this.transactionRepository.findByCustomer(transactions.get(0).getCustomer().getId())).willReturn(transactions);

		Collection<Transaction> gottenTransactions = this.transactionService
				.findByCustomerId(transactions.get(0).getCustomer().getId());

		assertThat(gottenTransactions).isEqualTo(transactions);
	}

	@Test
	public void getTransactionByItemId_shouldReturnGottenTransction() {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(TransactionGenerator.generateTransaction());
		given(this.transactionRepository.findByItem(transactions.get(0).getItem().getId())).willReturn(transactions);

		Collection<Transaction> gottenTransactions = this.transactionService
				.findByItemId(transactions.get(0).getItem().getId());

		assertThat(gottenTransactions).isEqualTo(transactions);
	}
}
