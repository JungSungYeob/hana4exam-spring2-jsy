package com.hana4.springexam2.service;

import java.util.List;

import com.hana4.springexam2.dto.CommentDTO;

public interface CommentService {
	public CommentDTO addComment(String pid, CommentDTO commentDTO);

	public List<CommentDTO> getAllCommentsByPid(String pid);

	public CommentDTO getCommentById(String id);

	public CommentDTO removeCommentById(String id);

	public CommentDTO modifyCommentByDTO(CommentDTO commentDTO);

}
