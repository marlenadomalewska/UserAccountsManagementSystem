package com.accountmanagement.data.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.accountmanagement.data.entity.AccountDTO;

@Service
public class AccountService {

	@Value("${server.port}")
	private String serverPort;

	public Collection<AccountDTO> getAllAccounts() {
		final RequestHeadersSpec<?> spec = WebClient.create().get()
			.uri("http://localhost:" + serverPort + "/account").accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML);

		final Collection<AccountDTO> accounts = spec.retrieve().toEntityList(AccountDTO.class).block().getBody();

		return accounts;
	}

	public AccountDTO getAccountById(int idAcc) {
		String url = "http://localhost:" + serverPort + "/account/{id_acc}";
		UriComponents uriComponents = UriComponentsBuilder.fromUriString(url).buildAndExpand(idAcc);

		final RequestHeadersSpec<?> spec = WebClient.create().get()
			.uri(uriComponents.toUriString()).accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML);

		final AccountDTO account = spec.retrieve().toEntity(AccountDTO.class).block().getBody();

		return account;
	}

	public void deleteAccount(int idAcc) {
		String url = "http://localhost:" + serverPort + "/account/{id_acc}";
		UriComponents uriComponents = UriComponentsBuilder.fromUriString(url).buildAndExpand(idAcc);

		WebClient.create().delete().uri(uriComponents.toUriString()).retrieve().bodyToMono(Void.class).block();
	}

	public void addAccount(AccountDTO account) {
		WebClient.create().post().uri("http://localhost:" + serverPort + "/account").body(BodyInserters.fromValue(account)).retrieve().bodyToMono(Void.class)
			.block();
	}

	public void editAccount(AccountDTO account) {
		WebClient.create().put().uri("http://localhost:" + serverPort + "/account").body(BodyInserters.fromValue(account)).retrieve().bodyToMono(Void.class)
			.block();
	}

	public boolean isUsernameAvailable(String username) {
		String url = "http://localhost:" + serverPort + "/account/check-username/{username}";
		UriComponents uriComponents = UriComponentsBuilder.fromUriString(url).buildAndExpand(username);

		final RequestHeadersSpec<?> spec = WebClient.create().get()
			.uri(uriComponents.toUriString());

		return spec.retrieve().toEntity(Boolean.class).block().getBody();

	}

}
