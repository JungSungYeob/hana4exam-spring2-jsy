package com.hana4.springexam2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hana4.springexam2.dao.CommentDAO;
import com.hana4.springexam2.dto.CommentDTO;

@Service
public class CommentServiceImpl implements CommentService {
	private final CommentDAO commentDAO;

	public CommentServiceImpl(CommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}

	@Override
	public CommentDTO addComment(String pid, CommentDTO commentDTO) {
		return null;
	}

	@Override
	public List<CommentDTO> getAllCommentsByPid(String pid) {
		return List.of();
	}

	@Override
	public CommentDTO getCommentById(String id) {
		return null;
	}

	@Override
	public CommentDTO removeCommentById(String id) {
		return null;
	}

	@Override
	public CommentDTO modifyCommentByDTO(CommentDTO commentDTO) {
		return null;
	}

}
