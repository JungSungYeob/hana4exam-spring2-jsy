package com.hana4.springexam2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hana4.springexam2.dto.PostDTO;
import com.hana4.springexam2.entity.Post;

@Service
public class PostServiceImpl implements PostService {
	@Override
	public List<Post> getAllPost() {
		return List.of();
	}

	@Override
	public PostDTO addPost(PostDTO postDTO) {
		return null;
	}

	@Override
	public Post getPost() {
		return null;
	}

	@Override
	public Post modifyPost(PostDTO postDTO) {
		return null;
	}

	@Override
	public Post removePost(Long id) {
		return null;
	}
}
