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

import com.hana4.springexam2.dto.CommentDTO;
import com.hana4.springexam2.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {
	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/{pid}")
	public CommentDTO createComment(@RequestBody CommentDTO commentDTO) {
		return commentService.addComment(commentDTO);
	}

	@GetMapping("/{pid}")
	public List<CommentDTO> getAllComments(@PathVariable String pid) {
		return commentService.getAllCommentsByPid(pid);
	}

	@DeleteMapping("/{id}")
	public CommentDTO deleteComment(@PathVariable Long id) {
		return commentService.removeCommentById(id);
	}

	@PatchMapping("/{id}")
	public CommentDTO updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
		commentDTO.setId(id);
		return commentService.modifyCommentByDTO(commentDTO);
	}
}
