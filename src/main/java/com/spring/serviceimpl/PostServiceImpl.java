package com.spring.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.spring.entities.Category;
import com.spring.entities.Post;
import com.spring.entities.User;
import com.spring.exception.ResourceNotFoundException;
import com.spring.payloads.PostDTO;
import com.spring.payloads.PostResponce;
import com.spring.repository.CategoryRepo;
import com.spring.repository.PostRepository;
import com.spring.repository.UserRepository;
import com.spring.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDTO createPost(PostDTO post, Integer userId, Integer categoryId) {
		Post post2 = this.modelMapper.map(post, Post.class);
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		post2.setImageName("default.png");
		post2.setAddedDate(new Date());
		post2.setUser(user);
		post2.setCategory(category);
		Post save = this.postRepository.save(post2);
		return this.modelMapper.map(save, PostDTO.class);
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer id) {
		Post post = this.postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "ID ", id));
		post.setContent(postDTO.getContent());
		post.setTitle(postDTO.getTitle());
		post.setImageName(postDTO.getImageName());
		Post post2 = this.postRepository.save(post);
		return this.modelMapper.map(post2, PostDTO.class);
	}

	@Override
	public void delete(Integer id) {
		Post post = this.postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "ID ", id));
		this.postRepository.delete(post);
	}

	@Override
	public PostDTO getPostById(Integer id) {
		Post post = this.postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "ID ", id));
		return this.modelMapper.map(post, PostDTO.class);
	}

	@Override
	public PostResponce getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		//sort by ascending/ descending
		Sort sort=null;
		if (sortDir.equals("asc")) {
			sort=Sort.by(sortBy).ascending();
		}else if(sortDir.equals("desc")) {
			sort=Sort.by(sortBy).descending();
		}
		//Pagination
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);		
	    Page<Post> pagePost = this.postRepository.findAll(p);
	    List<Post> list = pagePost.getContent();
		List<PostDTO> list2 = list.stream().map((e) -> this.modelMapper.map(e, PostDTO.class))
				.collect(Collectors.toList());
		PostResponce postResponce=new PostResponce();
		postResponce.setContent(list2);
		postResponce.setPageNumber(pagePost.getNumber());
		postResponce.setPageSize(pagePost.getSize());
		postResponce.setTotalElements(pagePost.getTotalElements());
		postResponce.setTotalPages(pagePost.getTotalPages());
		postResponce.setLastPage(pagePost.isLast());
		
		return postResponce;
	}

	@Override
	public List<PostDTO> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		List<Post> posts = this.postRepository.findByCategory(category);
		List<PostDTO> list = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return list;
	}

	@Override
	public List<PostDTO> getPostByUser(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		List<Post> posts = this.postRepository.findByUser(user);
		List<PostDTO> list = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return list;
	}

	@Override
	public List<PostDTO> searchPost(String keyword) {
		List<Post> posts = this.postRepository.findByTitle("%"+keyword+"%");
		List<PostDTO> list = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return list;
	}

}
