package com.spring.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.entities.Comments;
import com.spring.entities.Post;
import com.spring.exception.ResourceNotFoundException;
import com.spring.payloads.CommentsDTO;
import com.spring.repository.CommentRepository;
import com.spring.repository.PostRepository;
import com.spring.service.CommentService;


@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentRepository commentRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PostRepository postRepository;
	@Override
	public CommentsDTO createComment(CommentsDTO commentDto,Integer postId) {
		Comments comments = this.modelMapper.map(commentDto,Comments.class);
		Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		comments.setPost(post);
		Comments savedComment = this.commentRepo.save(comments);
		return this.modelMapper.map(savedComment, CommentsDTO.class);
	}

	@Override
	public List<CommentsDTO> getAllComment() {
		List<Comments> list = this.commentRepo.findAll();
		List<CommentsDTO> comments = list.stream().map((e)->this.modelMapper.map(e, CommentsDTO.class)).collect(Collectors.toList());
		return comments;
	}

	@Override
	public CommentsDTO getCommentById(Integer commentId) {
		Comments comments2 = this.commentRepo.findById(commentId).orElseThrow(()->
		new ResourceNotFoundException("Comment","commentId",commentId));
		return this.modelMapper.map(comments2, CommentsDTO.class);
	}

	@Override
	public CommentsDTO updateComment(Integer commentId, CommentsDTO commentDto) {
		Comments comments2 = this.commentRepo.findById(commentId).orElseThrow(()->
			new ResourceNotFoundException("Comment","commentId",commentId));
		comments2.setComment(commentDto.getComment());
		Comments savedComment = this.commentRepo.save(comments2);
		return  this.modelMapper.map(savedComment, CommentsDTO.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comments comments2 = this.commentRepo.findById(commentId).orElseThrow(()->
		new ResourceNotFoundException("Comment","commentId",commentId));
		this.commentRepo.delete(comments2);
		
	}

}
