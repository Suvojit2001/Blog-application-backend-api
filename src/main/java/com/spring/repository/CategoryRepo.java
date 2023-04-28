package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer>{

}
