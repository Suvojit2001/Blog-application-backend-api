package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entities.User;

public interface UserRepository extends JpaRepository<User,Integer>{

}
