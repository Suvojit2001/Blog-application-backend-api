package com.spring.service;


import java.util.List;

import com.spring.payloads.UserDTO;

public interface UserService {
		UserDTO createUser(UserDTO user);
		UserDTO updateUser(UserDTO user,Integer id);
		UserDTO getUserById(Integer id);
		List<UserDTO> getAllUser();
		void deleteUser(Integer id);
}
