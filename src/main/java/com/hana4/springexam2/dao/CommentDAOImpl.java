package com.hana4.springexam2.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.hana4.springexam2.dto.CommentDTO;
import com.hana4.springexam2.dto.CommentMapper;
import com.hana4.springexam2.entity.Comment;
import com.hana4.springexam2.entity.Post;
import com.hana4.springexam2.entity.User;
import com.hana4.springexam2.repository.CommentRepository;

@Component
public class CommentDAOImpl implements CommentDAO {
	private final CommentRepository commentRepository;

	public CommentDAOImpl(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	@Override
	public CommentDTO insert(CommentDTO commentDTO) {
		User writer = new User();
		writer.setId(commentDTO.getWriter());
		Post post = new Post();
		post.setId(commentDTO.getPost());
		Comment saveComment = commentRepository.save(CommentMapper.toComment(commentDTO, post, writer));
		return CommentMapper.toDTO(saveComment);
	}

	@Override
	public List<CommentDTO> findAll(String pid) {
		List<Comment> comments = commentRepository.findAll();
		return comments.stream().map(CommentMapper::toDTO).toList();
	}

	@Override
	public CommentDTO update(CommentDTO commentDTO) {
		Optional<Comment> comment = commentRepository.findById(commentDTO.getId());
		if (comment.isPresent()) {
			Comment updateComment = comment.get();
			updateComment.setBody(commentDTO.getBody());
			return CommentMapper.toDTO(commentRepository.save(updateComment));
		} else {
			throw new IllegalStateException("Comment not found");
		}
	}

	@Override
	public CommentDTO delete(Long id) {
		Optional<Comment> comment = commentRepository.findById(id);
		if (comment.isPresent()) {
			Comment deleteComment = comment.get();
			commentRepository.delete(deleteComment);
			return CommentMapper.toDTO(deleteComment);
		} else {
			throw new IllegalStateException("Comment not found");
		}
	}
}
