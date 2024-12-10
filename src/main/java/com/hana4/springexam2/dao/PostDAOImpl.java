package com.hana4.springexam2.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hana4.springexam2.dto.PostDTO;
import com.hana4.springexam2.dto.PostMapper;
import com.hana4.springexam2.entity.Post;
import com.hana4.springexam2.entity.User;
import com.hana4.springexam2.repository.PostRepository;

@Component
public class PostDAOImpl implements PostDAO {
	private PostRepository postRepository;

	public PostDAOImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Override
	public List<PostDTO> findAll() {
		List<Post> posts = postRepository.findAll();
		return posts.stream().map(PostMapper::toDTO).toList();
	}

	@Override
	public PostDTO insert(PostDTO postDTO) {
		User writer = new User();
		writer.setId(postDTO.getWriterId());
		Post savePost = postRepository.save(PostMapper.toPost(postDTO, writer));
		return PostMapper.toDTO(savePost);
	}

	@Override
	public PostDTO findById(Long id) {
		return null;
	}

	@Override
	public PostDTO update(PostDTO postDTO) {
		return null;
	}

	@Override
	public PostDTO delete(Long id) {
		return null;
	}
}
