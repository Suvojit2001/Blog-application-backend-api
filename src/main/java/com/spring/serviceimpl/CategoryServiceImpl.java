package com.spring.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.entities.Category;
import com.spring.exception.ResourceNotFoundException;
import com.spring.payloads.CategoryDTO;
import com.spring.repository.CategoryRepo;
import com.spring.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDTO createCategory(CategoryDTO catDto) {
		Category category = this.dtoToCategory(catDto);
		Category category2 = this.categoryRepo.save(category);
		return this.categoryToDto(category2);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO catDto, Integer catId) {
		Category category = this.categoryRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", catId));
		category.setCategoryTitle(catDto.getCategoryTitle());
		category.setCategoryDescription(catDto.getCategoryDescription());
		Category category2 = this.categoryRepo.save(category);
		return this.categoryToDto(category2);
	}

	@Override
	public void deleteCategory(Integer catId) {
		this.categoryRepo.deleteById(catId);
	}

	@Override
	public CategoryDTO getCategoryById(Integer catId) {
		Category category = this.categoryRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", catId));
		return this.categoryToDto(category);
	}

	@Override
	public List<CategoryDTO> getAllCategory() {
		List<Category> all = this.categoryRepo.findAll();
		List<CategoryDTO> list = all.stream().map(category -> this.categoryToDto(category))
				.collect(Collectors.toList());
		return list;
	}

	private Category dtoToCategory(CategoryDTO catDto) {
		Category category = this.modelMapper.map(catDto, Category.class);
		return category;
	}

	private CategoryDTO categoryToDto(Category category) {
		CategoryDTO dto = this.modelMapper.map(category, CategoryDTO.class);
		return dto;
	}
}
