package com.accountmanagement.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.accountmanagement.data.entity.Account;
import com.accountmanagement.data.entity.Gender;
import com.accountmanagement.data.service.AccountRepository;
import com.accountmanagement.data.service.AccountService;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AccountServiceTest {

	@Autowired
	private WebTestClient webClient;
	@Autowired
	private AccountService service;
	@Autowired
	private AccountRepository repository;

	@BeforeEach
	void setUp() {
		repository.delete(99999999);
		repository.addWithId(new Account(99999999, "Andrzej", "$2a$16$QN5/YFyGIR4mfZU28zobo.wLEdLEEiq7kUUEYEhf/BqJnF9FyfBJC", Gender.MALE, 0, null));
	}

	@AfterEach
	void tearDown() {
		repository.delete(99999999);
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
		int idAcc = 99999999;
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
