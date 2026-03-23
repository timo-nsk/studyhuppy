package com.studyhub.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@AllArgsConstructor
@Table("users")
public class AppUser  {
	@Id
	private Integer id;
	private UUID userId;
	private String mail;
	private String username;
	private String password;
	Boolean notificationSubscription;
	Boolean acceptedAgb;
	Integer semester;
	String profilbildPath;
}
