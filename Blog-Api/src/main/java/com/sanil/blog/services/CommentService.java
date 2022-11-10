package com.sanil.blog.services;

import com.sanil.blog.payloads.CommentDto;

public interface CommentService
{
	public CommentDto createComment(CommentDto commentDto, Integer postId);
	void deleteComment(Integer commentId);
}
