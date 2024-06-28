package com.accountmanagement.data.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accountmanagement.data.entity.AccountDTO;

@RestController
@RequestMapping("/account")
public class AccountController {

	private final AccountRepository repository;

	public AccountController(AccountRepository repository) {
		super();
		this.repository = repository;
	}

	@GetMapping("")
	public ResponseEntity<Collection<AccountDTO>> getAllAccounts() {
		return ResponseEntity.ok(repository.getAll());
	}

	@GetMapping(path = "/{id_acc}", produces = "application/json")
	public ResponseEntity<AccountDTO> getAccount(@PathVariable("id_acc") int idAcc) {
		Optional<AccountDTO> acc = repository.getById(idAcc);
		if (acc.isPresent()) {
			return ResponseEntity.ok(acc.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping(path = "/{id_acc}", produces = "application/json")
	public ResponseEntity<Void> deleteAccount(@PathVariable("id_acc") int idAcc) {
		repository.delete(idAcc);
		return ResponseEntity.ok().build();
	}

	@PostMapping("")
	public ResponseEntity<Void> addAccount(@RequestBody AccountDTO account) {
		repository.add(account);
		return ResponseEntity.ok().build();
	}

	@PutMapping("")
	public ResponseEntity<Void> editAccount(@RequestBody AccountDTO account) {
		repository.edit(account);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/check-username/{username}")
	public ResponseEntity<Boolean> isUsernameAvailable(@PathVariable("username") String username) {
		return ResponseEntity.ok(repository.isUsernameAvailable(username));
	}
}
