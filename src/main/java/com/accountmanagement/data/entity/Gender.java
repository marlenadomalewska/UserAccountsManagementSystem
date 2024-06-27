package com.accountmanagement.data.entity;

import lombok.Getter;

@Getter
public enum Gender {

	MALE("M", "Male"),
	FEMALE("F", "Female"),
	OTHER("O", "Other");

	private String value;
	private String description;

	private Gender(String value, String description) {
		this.value = value;
		this.description = description;
	}

}
