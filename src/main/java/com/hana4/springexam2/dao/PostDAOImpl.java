package com.hana4.springexam2.dao;

import java.util.List;
import java.util.Optional;

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
		writer.setId(postDTO.getWriter());
		Post savePost = postRepository.save(PostMapper.toPost(postDTO, writer));
		return PostMapper.toDTO(savePost);
	}

	@Override
	public PostDTO findById(Long id) {
		Optional<Post> post = postRepository.findById(id);
		return post.map(PostMapper::toDTO).orElse(null);
	}

	@Override
	public PostDTO update(PostDTO postDTO) {
		Optional<Post> post = postRepository.findById(postDTO.getId());
		if (post.isPresent()) {
			Post savePost = post.get();
			savePost.setTitle(postDTO.getTitle());
			savePost.setBody(postDTO.getBody());
			return PostMapper.toDTO(postRepository.save(savePost));
		} else {
			throw new IllegalStateException("Post not found");
		}
	}

	@Override
	public PostDTO delete(Long id) {
		Optional<Post> post = postRepository.findById(id);
		if (post.isPresent()) {
			Post delPost = post.get();
			System.out.println("hi");
			postRepository.delete(delPost);
			System.out.println("bye");
			return PostMapper.toDTO(delPost);
		} else {
			throw new IllegalStateException("Post not found");
		}
	}
}
