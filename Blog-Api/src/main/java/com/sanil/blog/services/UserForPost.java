package com.sanil.blog.services;

import com.sanil.blog.payloads.UserDto;

public interface UserForPost
{
   UserDto createUserForPost(UserDto userDto, Integer postId);
   void deleteUserFromPost(Integer userId);
}
