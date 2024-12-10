package com.hana4.springexam2.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
	private Long id;
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	private Long post;    // Post ID
	private String writer; // User ID
	private String body;
}