package com.sanil.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto
{
	private int id;
	
	@NotEmpty
	@Size(min=4,message = "username must be greater then 4 character")
	private String name;
	
	@Email(message = "Email address is not valid !!")
	private String email;
	
	@NotEmpty
	@Size(min= 3, max =10,message= "password must be minimum of 3 char and max of 10 char")
	private String password;
	
	@NotEmpty
	private String about;

}
