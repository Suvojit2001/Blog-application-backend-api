package com.spring.service;

import java.util.List;


import com.spring.payloads.CommentsDTO;

public interface CommentService {
	public CommentsDTO createComment(CommentsDTO comment,Integer postId); 
	public List<CommentsDTO> getAllComment();
	public CommentsDTO getCommentById(Integer commentId);
	public CommentsDTO updateComment(Integer commentId,CommentsDTO comment);
	public void deleteComment(Integer commentId);
	
}
