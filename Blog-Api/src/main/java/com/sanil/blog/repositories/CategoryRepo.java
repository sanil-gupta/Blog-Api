package com.sanil.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanil.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer> {

}
