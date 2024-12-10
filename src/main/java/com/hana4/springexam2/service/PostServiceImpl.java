package com.hana4.springexam2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hana4.springexam2.dao.PostDAO;
import com.hana4.springexam2.dto.PostDTO;

@Service
public class PostServiceImpl implements PostService {
	private final PostDAO postDAO;

	public PostServiceImpl(PostDAO postDAO) {
		this.postDAO = postDAO;
	}

	@Override
	public List<PostDTO> getAllPost() {
		return postDAO.findAll();
	}

	@Override
	public PostDTO addPost(PostDTO postDTO) {
		return postDAO.insert(postDTO);
	}

	@Override
	public PostDTO getPost(Long id) {
		return postDAO.findById(id);
	}

	@Override
	public PostDTO modifyPost(PostDTO postDTO) {
		return postDAO.update(postDTO);
	}

	@Override
	public PostDTO removePost(Long id) {
		return postDAO.delete(id);
	}
}
