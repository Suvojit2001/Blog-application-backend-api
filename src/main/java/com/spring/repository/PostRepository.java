package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.entities.Category;
import com.spring.entities.Post;
import com.spring.entities.User;

public interface PostRepository extends JpaRepository<Post,Integer> {
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	// searching
	@Query("select p from Post p where p.title like :key")
	List<Post> findByTitle(@Param("key") String title);
}
