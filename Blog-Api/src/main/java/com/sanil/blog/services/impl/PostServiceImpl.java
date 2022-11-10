package com.sanil.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sanil.blog.entities.Category;
import com.sanil.blog.entities.Post;
import com.sanil.blog.entities.User;
import com.sanil.blog.exceptions.ResourceNotFoundException;
import com.sanil.blog.payloads.PostDto;
import com.sanil.blog.payloads.PostResponse;
import com.sanil.blog.repositories.CategoryRepo;
import com.sanil.blog.repositories.PostRepo;
import com.sanil.blog.repositories.UserRepo;
import com.sanil.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService 
{
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	private Optional<Post> findById;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) 
	{
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "user id", userId));
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category id",categoryId));
		
		Post post =	this.modelMapper.map(postDto,Post.class);
		post.setImageName("default.png");
		post.setAddDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) 
	{
	   Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));
	   post.setTitle(postDto.getTitle());
	   post.setContent(postDto.getContent());
	   post.setImageName(postDto.getImageName());
	   
	   Post updatedPost = this.postRepo.save(post);
	   return this.modelMapper.map(updatedPost, PostDto.class);	
	}

	@Override
	public void deletePost(Integer postId) 
	{
      Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","PostId",postId));
	  this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy)
	{
		
		//create pageable object
		 Pageable p = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy).descending());
		
		 Page<Post> pagePost = this.postRepo.findAll(p);
		 List<Post> allPosts = pagePost.getContent();
		
		List<PostDto> postDto = allPosts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		//postResponse obj to store data
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements( pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId)
	{
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post_id",postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) 
	{
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "category id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
	    return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId)
	{
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	@Override
	public List<PostDto> searchPosts(String keyword) 
	{
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos =posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
