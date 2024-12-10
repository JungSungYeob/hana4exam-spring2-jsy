package com.hana4.springexam2.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hana4.springexam2.dto.PostDTO;
import com.hana4.springexam2.entity.Post;
import com.hana4.springexam2.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping()
	public PostDTO addPost(@RequestBody PostDTO postDTO) {
		return postService.addPost(postDTO);
	}

	@GetMapping("/list")
	public List<Post> getAllPosts() {
		return postService.getAllPost();
	}

	@GetMapping("/{id}")
	public Post getPostById(@PathVariable Long id) {
		return postService.getPost();
	}

	@PatchMapping("/{id}")
	public Post updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
		postDTO.setId(id);
		return postService.modifyPost(postDTO);
	}

	@DeleteMapping("/{id}")
	public Post deletePost(@PathVariable Long id) {
		return postService.removePost(id);
	}

}