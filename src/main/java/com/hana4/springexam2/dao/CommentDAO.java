package com.hana4.springexam2.dao;

import java.util.List;

import com.hana4.springexam2.dto.CommentDTO;

public interface CommentDAO {
	CommentDTO insert(CommentDTO commentDTO);

	List<CommentDTO> findAll(String pid);

	CommentDTO update(CommentDTO commentDTO);

	CommentDTO delete(Long id);
}
