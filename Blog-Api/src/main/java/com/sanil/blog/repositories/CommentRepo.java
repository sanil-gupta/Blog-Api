package com.sanil.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanil.blog.entities.Comment;


public interface CommentRepo extends JpaRepository<Comment, Integer>
{
}
