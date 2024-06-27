package com.accountmanagement.data.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {

	private int idAcc;
	private String username;
	private Gender gender;
	private int age;
	private LocalDateTime creationTimestamp;
}
