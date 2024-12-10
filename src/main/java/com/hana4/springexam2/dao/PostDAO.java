package com.hana4.springexam2.dao;

import java.util.List;

import com.hana4.springexam2.dto.PostDTO;

public interface PostDAO {
	List<PostDTO> findAll();

	PostDTO insert(PostDTO postDTO);

	PostDTO findById(Long id);

	PostDTO update(PostDTO postDTO);

	PostDTO delete(Long id);
}
