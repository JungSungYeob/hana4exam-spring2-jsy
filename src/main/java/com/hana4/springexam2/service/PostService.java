package com.hana4.springexam2.service;

import java.util.List;

import com.hana4.springexam2.dto.PostDTO;
import com.hana4.springexam2.entity.Post;

public interface PostService {
	public List<Post> getAllPost();

	public PostDTO addPost(PostDTO postDTO);

	public Post getPost();

	Post modifyPost(PostDTO postDTO);

	Post removePost(Long id);
}
