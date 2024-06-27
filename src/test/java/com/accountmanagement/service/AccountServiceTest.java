package com.accountmanagement.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import com.accountmanagement.data.entity.Account;
import com.accountmanagement.data.service.AccountService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountServiceTest {

	@Autowired
	private WebClient webClient;
	@Autowired
	private AccountService service;

	@BeforeEach
	void setUp() {

	}

	@AfterEach
	void tearDown() {

	}

	@Test
	void webClientNotNull() {
		// Given
		// When
		// Then
		assertNotNull(webClient);
	}

	@Test
	void serviceNotNull() {
		// Given
		// When
		// Then
		assertNotNull(service);
	}

	@Test
	void getAllAccountsOk() {
		// Given
		// When
		Collection<Account> accounts = service.getAllAccounts();
		// Then
		Assertions.assertThat(accounts).isNotEmpty();
	}

	@Test
	void getAccountByIdOk() {
		// Given
		int idAcc = 1;
		// When
		Account account = service.getAccountById(idAcc);
		// Then
		Assertions.assertThat(account).isNotNull();
	}

	@Test
	void isUsernameAvailableOk() {
		// Given
		String username = "X";
		// When
		boolean available = service.isUsernameAvailable(username);
		// Then
		Assertions.assertThat(available).isTrue();
	}
}
