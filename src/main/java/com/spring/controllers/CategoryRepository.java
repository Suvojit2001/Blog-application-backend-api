package com.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.payloads.ApiResponse;
import com.spring.payloads.CategoryDTO;
import com.spring.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryRepository {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO catDto = null;
		try {
			catDto = this.categoryService.createCategory(categoryDTO);
			return new ResponseEntity<CategoryDTO>(catDto, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<CategoryDTO>(catDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO catDto,
			@PathVariable("id") Integer catId) {
		CategoryDTO category = this.categoryService.updateCategory(catDto, catId);
		return new ResponseEntity<CategoryDTO>(category, HttpStatus.CREATED);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("id") Integer catId) {
			this.categoryService.deleteCategory(catId);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully", true),
					HttpStatus.OK);	
	}

	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getAllCategory() {
		try {
			List<CategoryDTO> list = this.categoryService.getAllCategory();
			return new ResponseEntity<List<CategoryDTO>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getCategorybyId(@PathVariable("id") Integer id) {
		
			CategoryDTO categoryDTO = this.categoryService.getCategoryById(id);
			return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
		
	}

}
