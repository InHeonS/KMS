package com.example.kms.DTO;

import lombok.Data;

@Data
public class User {
	private String id;
	private String password;
	private String name;
	private String compNm;
	private String compId;
	private String phone;
	private String email;
	private int auth;
}
