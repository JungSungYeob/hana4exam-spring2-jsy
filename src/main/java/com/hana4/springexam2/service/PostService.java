package com.hana4.springexam2.service;

import java.util.List;

import com.hana4.springexam2.dto.PostDTO;

public interface PostService {
	public List<PostDTO> getAllPost();

	public PostDTO addPost(PostDTO postDTO);

	public PostDTO getPost(Long id);

	PostDTO modifyPost(PostDTO postDTO);

	PostDTO removePost(Long id);
}
