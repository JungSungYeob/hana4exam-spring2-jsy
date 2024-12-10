package com.hana4.springexam2.dto;

import com.hana4.springexam2.entity.Comment;
import com.hana4.springexam2.entity.Post;
import com.hana4.springexam2.entity.User;

public class CommentMapper {
	public static CommentDTO toDTO(Comment comment) {
		return CommentDTO.builder()
			.id(comment.getId())
			.createAt(comment.getCreateAt())
			.updateAt(comment.getUpdateAt())
			.post(comment.getPost().getId())
			.writer(comment.getWriter().getId())
			.body(comment.getBody())
			.build();
	}

	public static Comment toComment(CommentDTO commentDTO, Post post, User writer) {
		return new Comment(commentDTO.getBody(), post, writer);
	}
}
