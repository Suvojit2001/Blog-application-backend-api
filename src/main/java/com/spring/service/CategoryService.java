package com.spring.service;

import java.util.List;

import com.spring.payloads.CategoryDTO;

public interface CategoryService {
 
	//create
	public CategoryDTO createCategory(CategoryDTO catDto);
	//update
	public CategoryDTO updateCategory(CategoryDTO catDto,Integer catId);
	//delete
	public void deleteCategory(Integer catId);
	//get single data
	public CategoryDTO getCategoryById(Integer catId);
	//get all data
	public List<CategoryDTO> getAllCategory();
}
