package com.spring.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.exception.ResourceNotFoundException;
import com.spring.entities.User;
import com.spring.payloads.UserDTO;
import com.spring.repository.UserRepository;
import com.spring.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDTO createUser(UserDTO user) {
		User user2 =this.DTOToUser(user);
		User user3 = this.userRepository.save(user2);
		return this.userToDTO(user3);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer id) {
		User  user=this.userRepository.findById(id).orElseThrow(
				()->new ResourceNotFoundException("User","id",id));
		user.setAbout(userDTO.getAbout());
		user.setEmail(userDTO.getEmail());
		user.setName(userDTO.getName());
		user.setPassword(userDTO.getPassword());
		
		User user2=this.userRepository.save(user);
		
		return this.userToDTO(user2);
	}

	@Override
	public UserDTO getUserById(Integer id) {
		User  user=this.userRepository.findById(id).orElseThrow(
				()->new ResourceNotFoundException("User","id",id));
		
		return this.userToDTO(user); 
	}

	@Override
	public List<UserDTO> getAllUser() {
		List<User> all = this.userRepository.findAll();
		List<UserDTO> list = all.stream().map(user->this.userToDTO(user)).collect(Collectors.toList());
		return list;
	}

	@Override
	public void deleteUser(Integer id) {
		this.userRepository.deleteById(id);
	}

	private User DTOToUser(UserDTO userDTO) {
		User user=this.modelMapper.map(userDTO, User.class);
		return user;
	}
	
	private UserDTO userToDTO(User user) {
		UserDTO userDTO=this.modelMapper.map(user, UserDTO.class);
		
		return userDTO;
	}
}
