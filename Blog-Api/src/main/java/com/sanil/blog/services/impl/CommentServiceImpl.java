package com.sanil.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanil.blog.entities.Comment;
import com.sanil.blog.entities.Post;
import com.sanil.blog.exceptions.ResourceNotFoundException;
import com.sanil.blog.payloads.CommentDto;
import com.sanil.blog.repositories.CommentRepo;
import com.sanil.blog.repositories.PostRepo;
import com.sanil.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService 
{
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId)
	{
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
	    
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		
		Comment saveComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(saveComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId)
	{
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "CommentId", commentId));
	      
		this.commentRepo.delete(comment);
	  
	}

}
