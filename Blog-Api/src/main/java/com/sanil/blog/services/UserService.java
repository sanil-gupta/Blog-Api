package com.sanil.blog.services;

import java.util.List;

import com.sanil.blog.payloads.UserDto;

public interface UserService 
{ 
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deteleUser(Integer userId);
	

}
