package com.sanil.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanil.blog.exceptions.*;

import com.sanil.blog.payloads.UserDto;
import com.sanil.blog.repositories.PostRepo;
import com.sanil.blog.repositories.UserRepo;
import com.sanil.blog.services.UserService;
import com.sanil.blog.entities.*;

@Service
public class UserServiceImpl implements UserService 
{
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PostRepo postRepo;

	@Override
	public UserDto createUser(UserDto userDto) 
	{
		User user = this.dtoTOUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) 
	{
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User"," Id ",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = this.userRepo.save(user);
		return this.userToDto(updatedUser);
		
	}

	@Override
	public UserDto getUserById(Integer userId) 
	{
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," Id ",userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() 
	{
	  List<User> users = this.userRepo.findAll();
	  List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
	  
	  return userDtos;
	}

	@Override
	public void deteleUser(Integer userId)
	{
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		this.userRepo.delete(user);
	}

	private User dtoTOUser(UserDto userDto)
	{
		//modelMapper
		User user = this.modelMapper.map(userDto,User.class);
		
		//convert one obj to another obj without modelMapper class
		
		/*	User user = new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword()); */ 
		return user;
	}
	private UserDto userToDto(User user)
	{
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
		
	
		//convert one obj to another obj without modelMapper class
		
		/*	UserDto userDto = new UserDto();
		userDto.setId(user.getId()); 
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setAbout(user.getAbout());
		userDto.setPassword(user.getPassword());
		return userDto; */
	}
}
