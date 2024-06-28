package com.accountmanagement.service;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.accountmanagement.data.entity.Account;
import com.accountmanagement.data.entity.Gender;
import com.accountmanagement.data.service.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@AutoConfigureMockMvc
@SpringBootTest
public class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AccountRepository repository;
	@Autowired
	private ServerProperties serverProperties;

	private static String BASE_URL;

	private static ObjectMapper getObjectMapper() {
		return JsonMapper.builder().addModule(new JavaTimeModule()).build();
	}

	@BeforeEach
	void setUp() {
		Integer port = serverProperties.getPort();
		BASE_URL = new StringBuilder("http://localhost:")
			.append(port).append("/account").toString();
	}

	@AfterEach
	void tearDown() {

	}

	@Test
	void getAllAccountsOk() throws Exception {
		// Given
		Collection<Account> accounts = List.of(new Account(), new Account());
		// When
		Mockito.when(repository.getAll()).thenReturn(accounts);

		// Then
		MockHttpServletRequestBuilder action = MockMvcRequestBuilders.get(BASE_URL);
		action.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(action).andExpect(status().isOk()).andDo(print()).andReturn();
	}

	@Test
	void getAccountByIdOk() throws Exception {
		// Given
		int idAcc = 1;
		Optional<Account> optAccount = Optional.ofNullable(new Account());
		// When
		Mockito.when(repository.getById(1)).thenReturn(optAccount);

		// Then
		MockHttpServletRequestBuilder action = MockMvcRequestBuilders.get(BASE_URL + "/{id_acc}", idAcc);
		action.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(action).andExpect(status().isOk()).andDo(print()).andReturn();
	}

	@Test
	void getAccountByIdNotOk() throws Exception {
		// Given
		int idAcc = 0;
		// When
		Mockito.when(repository.getById(1)).thenReturn(Optional.empty());

		// Then
		MockHttpServletRequestBuilder action = MockMvcRequestBuilders.get(BASE_URL + "/{id_acc}", idAcc);
		action.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(action).andExpect(status().is4xxClientError()).andDo(print()).andReturn();
	}

	@Test
	void addAccountOk() throws Exception {
		// Given
		Account account = new Account(0, "AAA", "XYZ", Gender.FEMALE, 9, null);
		// When
		String payload = getObjectMapper().writeValueAsString(account);
		// Then
		MockHttpServletRequestBuilder action = MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(payload);

		mockMvc.perform(action).andExpect(status().isOk()).andReturn();
	}

	@Test
	void editAccountOk() throws Exception {
		// Given
		Account account = new Account(1, "AAA", null, Gender.FEMALE, 9, null);
		// When
		String payload = getObjectMapper().writeValueAsString(account);
		// Then
		MockHttpServletRequestBuilder action = MockMvcRequestBuilders.put(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(payload);

		mockMvc.perform(action).andExpect(status().isOk()).andReturn();
	}

	@Test
	void isUsernameAvailableOk() throws Exception {
		// Given
		String username = "X";
		// When
		MockHttpServletRequestBuilder action = MockMvcRequestBuilders.get(BASE_URL + "/check-username/{username}", username);
		// Then
		mockMvc.perform(action).andExpect(status().isOk()).andReturn();
	}

	@Test
	void deleteAccountOk() throws Exception {
		// Given
		int idAcc = 1;
		// When
		// Then
		MockHttpServletRequestBuilder action = MockMvcRequestBuilders.delete(BASE_URL + "/{id_acc}", idAcc);

		mockMvc.perform(action).andExpect(status().isOk()).andReturn();
	}
}
