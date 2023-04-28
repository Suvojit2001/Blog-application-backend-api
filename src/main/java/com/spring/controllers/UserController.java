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
import com.spring.payloads.UserDTO;
import com.spring.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	// Add user
	@PostMapping("/")
	public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO) {
		UserDTO user = null;
		try {
			user = this.userService.createUser(userDTO);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// Update User
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody UserDTO dto) {

		UserDTO user = this.userService.updateUser(dto, id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	// Delete User
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> delete(@PathVariable("id") Integer id) {
		this.userService.deleteUser(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
	}

	// get All user

	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUser() {
		try {
			List<UserDTO> user = this.userService.getAllUser();
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// get user By id
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Integer id) {

		UserDTO userDTO = this.userService.getUserById(id);
		return new ResponseEntity<>(userDTO, HttpStatus.OK);

	}

}
