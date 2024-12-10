package com.hana4.springexam2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UserDTO {
	private String id;

	@Schema(description = "유저이름", example = "Kim1")
	private String name;

	@Schema(description = "유저이메일", example = "kim1@gmail.com")
	private String email;
}
