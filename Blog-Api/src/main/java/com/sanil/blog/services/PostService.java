package com.sanil.blog.services;

import java.util.List;


import com.sanil.blog.payloads.PostDto;
import com.sanil.blog.payloads.PostResponse;

public interface PostService 
{
	//create
	PostDto createPost(PostDto post,Integer userId,Integer categoryId);
	
	//update
	PostDto updatePost(PostDto post, Integer postId);
	
	//delete
    void deletePost(Integer postId);
    
    //get all posts
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy);

    //get single post by using postid
    PostDto getPostById(Integer postId);
    
    //get all posts by category
    List<PostDto> getPostsByCategory(Integer categoryId);
    
    //get all posts by user
    List<PostDto> getPostsByUser(Integer userId);
    
    //search posts
    List<PostDto> searchPosts(String keyword);
}
