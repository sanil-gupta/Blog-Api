package com.sanil.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sanil.blog.exceptions.ApiResponse;
import com.sanil.blog.payloads.PostDto;
import com.sanil.blog.payloads.PostResponse;
import com.sanil.blog.services.FileService;
import com.sanil.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController 
{
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
		
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
											  @PathVariable Integer userId,
											  @PathVariable Integer categoryId)
	{
		PostDto  createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
	}
	
	//get post by user
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId)
	{
		List<PostDto> posts = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	//get post by category
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId)
		{
			List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
			return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		}
		
	//get all the post
		
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value="pageNumber",defaultValue = "00",required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "2",required = false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = "postId",required = false)String sortBy
			)
	{
		PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	//get post details by id
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
	{
	    PostDto postDto = this.postService.getPostById(postId);
	    return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);   
	}
	
	//delete post by id
	
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId)
	{
		this.postService.deletePost(postId);
		return new ApiResponse("Post is successfully deleted",true);
	}
	
	//update post by id
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId)
	{
		PostDto updatedpost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedpost,HttpStatus.OK);
	}
	
	//search of post
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> serachPostByTitle(@PathVariable String keywords)
	{
		List<PostDto> result = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	
	//post-image upload
	
	@PostMapping("/post/image/upload/{postId}")
     public ResponseEntity<PostDto> uploadPostImage(
    		 @RequestParam MultipartFile image,
    		 @PathVariable Integer postId) throws IOException
     {
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);	
    	return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
     }
	
	//method to serve files
	
	@GetMapping(value ="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName, 
			HttpServletResponse response) throws IOException
	{
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());	
	}
}
