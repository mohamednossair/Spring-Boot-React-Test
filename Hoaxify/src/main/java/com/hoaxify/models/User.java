package com.hoaxify.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class User {
	@Id
	@GeneratedValue
	private long id;
	
	@NotNull(message = "Username cannot be null")
	@Size(min = 4,max = 128)
	
	private String userName;
	
	@NotNull
	@Size(min = 4,max = 128)
	private String displayName;
	
	@NotNull
	@Size(min = 8,max = 256)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
	private String password;
}
