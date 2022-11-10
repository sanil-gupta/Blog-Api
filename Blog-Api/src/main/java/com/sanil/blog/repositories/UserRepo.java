package com.sanil.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanil.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> 
{

}
