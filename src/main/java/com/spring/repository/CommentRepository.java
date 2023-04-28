package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entities.Comments;

public interface CommentRepository extends JpaRepository<Comments, Integer>{

}
