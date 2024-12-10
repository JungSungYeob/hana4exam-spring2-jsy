package com.hana4.springexam2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hana4.springexam2.dao.CommentDAO;
import com.hana4.springexam2.dto.CommentDTO;

import jakarta.transaction.Transactional;

@Service
public class CommentServiceImpl implements CommentService {
	private final CommentDAO commentDAO;

	public CommentServiceImpl(CommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}

	@Override
	public CommentDTO addComment(CommentDTO commentDTO) {
		return commentDAO.insert(commentDTO);
	}

	@Override
	public List<CommentDTO> getAllCommentsByPid(String pid) {
		return commentDAO.findAll(pid);
	}

	@Override
	@Transactional
	public CommentDTO removeCommentById(Long id) {
		return commentDAO.delete(id);
	}

	@Override
	public CommentDTO modifyCommentByDTO(CommentDTO commentDTO) {
		return commentDAO.update(commentDTO);
	}
}
