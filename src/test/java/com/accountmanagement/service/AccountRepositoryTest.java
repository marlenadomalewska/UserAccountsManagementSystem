package com.accountmanagement.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.accountmanagement.data.entity.Account;
import com.accountmanagement.data.entity.Gender;
import com.accountmanagement.data.service.AccountRepository;

@SpringBootTest
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository repository;

	@BeforeEach
	void setUp() {
		repository.delete(99999999);
		repository.addWithId(new Account(99999999, "Andrzej", Gender.MALE, 0, null));
	}

	@AfterEach
	void tearDown() {

	}

	@Test
	void repositoryNotNull() {
		// Given
		// When
		// Then
		assertNotNull(repository);
	}

	@Test
	void getAllOk() {
		// Given
		// When
		Collection<Account> accounts = repository.getAll();
		// Then
		Assertions.assertThat(accounts).isNotEmpty();
	}

	@Test
	void getByIdOk() {
		// Given
		int idAcc = 99999999;
		// When
		Optional<Account> account = repository.getById(idAcc);
		// Then
		Assertions.assertThat(account).isNotEmpty();
	}

	@Test
	void getByIdNotOk() {
		// Given
		int idAcc = 0;
		// When
		Optional<Account> account = repository.getById(idAcc);
		// Then
		Assertions.assertThat(account).isEmpty();
	}

	@Test
	void addAccountOk() {
		// Given
		Account account = new Account();
		account.setAge(5);
		account.setGender(Gender.FEMALE);
		account.setUsername("anna");
		// When
		// Then
		assertDoesNotThrow(() -> repository.add(account));
	}

	@Test
	void addAccountNotOk() {
		// Given
		Account account = new Account();
		// When
		// Then
		assertThrows(Exception.class, () -> repository.add(account));
	}

	@Test
	void editAccountOk() {
		// Given
		Account account = new Account();
		account.setAge(5);
		account.setGender(Gender.FEMALE);
		account.setIdAcc(99999999);
		// When
		// Then
		assertDoesNotThrow(() -> repository.edit(account));
	}

	@Test
	void editAccountNotOk() {
		// Given
		Account account = new Account();
		// When
		// Then
		assertThrows(Exception.class, () -> repository.edit(account));
	}

	@Test
	void isUsernameAvailableOk() {
		// Given
		String username = "X";
		// When
		boolean isAvailable = repository.isUsernameAvailable(username);
		// Then
		Assertions.assertThat(isAvailable).isTrue();
	}

	@Test
	void isUsernameNotAvailableOk() {
		// Given
		String username = "anna";
		// When
		boolean isAvailable = repository.isUsernameAvailable(username);
		// Then
		Assertions.assertThat(isAvailable).isFalse();
	}

	@Test
	void deleteAccountOk() {
		// Given
		int idAcc = 99999999;
		// When
		// Then
		assertDoesNotThrow(() -> repository.delete(idAcc));
	}
}
