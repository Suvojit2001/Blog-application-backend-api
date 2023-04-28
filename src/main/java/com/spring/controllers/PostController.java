package com.spring.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.spring.config.AppConstants;
import com.spring.payloads.ApiResponse;
import com.spring.payloads.PostDTO;
import com.spring.payloads.PostResponce;
import com.spring.service.FileService;
import com.spring.service.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;
	// create
	@PostMapping("/users/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		try {
			PostDTO post = this.postService.createPost(postDTO, userId, categoryId);
			return new ResponseEntity<PostDTO>(post, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<PostDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/users/{userId}/posts")
	public ResponseEntity<List<PostDTO>> getAllPostByUser(@PathVariable Integer userId) {

		List<PostDTO> list = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDTO>>(list, HttpStatus.OK);

	}

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDTO>> getAllPostByCategory(@PathVariable Integer categoryId) {

		List<PostDTO> list = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDTO>>(list, HttpStatus.OK);

	}

	@GetMapping("/posts")
	public ResponseEntity<PostResponce> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		PostResponce postResponce = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponce>(postResponce, HttpStatus.OK);

	}

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId) {

		PostDTO post = this.postService.getPostById(postId);
		return new ResponseEntity<PostDTO>(post, HttpStatus.OK);

	}

	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		this.postService.delete(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully", true), HttpStatus.OK);
	}

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Integer postId) {
		PostDTO post = this.postService.updatePost(postDTO, postId);
		return new ResponseEntity<PostDTO>(post, HttpStatus.OK);
	}

	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable String keywords) {
		List<PostDTO> result = this.postService.searchPost(keywords);
		return new ResponseEntity<List<PostDTO>>(result, HttpStatus.OK);
	}

	// post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image") MultipartFile image 
			,@PathVariable Integer postId) throws IOException {
		PostDTO postDTO = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDTO.setImageName(fileName);
		PostDTO updatePost = this.postService.updatePost(postDTO, postId);
	    return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);
	}
	
	//serving post
	@GetMapping(value="/post/image/{imageName}" ,produces = MediaType.IMAGE_JPEG_VALUE)
	public void getImage(HttpServletResponse response
			,@PathVariable String imageName)throws IOException {
		InputStream stream = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(stream,response.getOutputStream());
	}
	
}
