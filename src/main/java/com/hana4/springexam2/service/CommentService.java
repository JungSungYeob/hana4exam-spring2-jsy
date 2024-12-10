package com.hana4.springexam2.service;

import java.util.List;

import com.hana4.springexam2.dto.CommentDTO;

public interface CommentService {
	public CommentDTO addComment(CommentDTO commentDTO);

	public List<CommentDTO> getAllCommentsByPid(String pid);

	public CommentDTO removeCommentById(Long id);

	public CommentDTO modifyCommentByDTO(CommentDTO commentDTO);

}
