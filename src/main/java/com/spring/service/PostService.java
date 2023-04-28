package com.spring.service;

import java.util.List;


import com.spring.payloads.PostDTO;
import com.spring.payloads.PostResponce;

public interface PostService {
	    //create
		PostDTO createPost(PostDTO post,Integer userId,Integer categoryId);
		
		//update
		PostDTO updatePost(PostDTO postDTO,Integer id);
		
		//delete
		void delete(Integer id);
		
		//get single post
		PostDTO getPostById(Integer id);
		
		// get All post		
		PostResponce getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
		
		//get All post by category
		List<PostDTO> getPostsByCategory(Integer categoryId);
		
		//get all post By user
		List<PostDTO> getPostByUser(Integer userId);
		
		//search post
		List<PostDTO> searchPost(String keyword);
}
