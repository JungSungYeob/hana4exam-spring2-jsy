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
public class PostDTO {
	private Long id;
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	private String title;
	private String writer;
	private String body;

	public PostDTO(String title, String writer, String body) {
		this.title = title;
		this.writer = writer;
		this.body = body;
	}
}

