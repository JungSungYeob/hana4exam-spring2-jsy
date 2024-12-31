package com.hana4.springexam2.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hana4.springexam2.entity.Comment;
import com.hana4.springexam2.entity.Post;
import com.hana4.springexam2.entity.User;
import com.hana4.springexam2.repository.CommentRepository;
import com.hana4.springexam2.repository.PostRepository;
import com.hana4.springexam2.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	public void setup() {
		User writer = new User("testUser", "test@example.com");
		userRepository.save(writer);

		Post post = new Post("Initial Title", writer, "Initial Body");
		postRepository.save(post);

		for (short n = 1; n <= 5; n++) {
			commentRepository.save(new Comment("Initial Comment" + n, post, writer));
		}
	}

	@Test
	@Order(1)
	void createComment() throws Exception {
		User writer = userRepository.findAll().get(0);
		Post post = postRepository.findAll().get(0);

		Map<String, String> request = new HashMap<>();
		request.put("body", "Sample comment title");
		request.put("post", post.getId().toString());
		request.put("writer", writer.getId());
		mockMvc.perform(post("/comments/{id}", post.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.body").value("Sample comment title"))
			.andExpect(jsonPath("$.post").value(post.getId().toString()))
			.andExpect(jsonPath("$.writer").value(writer.getId()))
			.andExpect(jsonPath("$.createAt").isNotEmpty())
			.andExpect(jsonPath("$.updateAt").isNotEmpty())
			.andExpect(jsonPath("$.id").isNotEmpty())
			.andDo(print());
	}

	@Test
	@Order(2)
	void getComments() throws Exception {
		Post post = postRepository.findAll().get(0);
		mockMvc.perform(get("/comments/{id}", post.getId()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$.length()").value(5));
	}

	@Test
	@Order(3)
	void updateComment() throws Exception {
		Comment comment = commentRepository.findAll().get(0);

		Map<String, String> request = new HashMap<>();
		request.put("body", "Updated comment title");

		mockMvc.perform(patch("/comments/{id}", comment.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.body").value("Updated comment title"))
			.andExpect(jsonPath("$.post").value(comment.getPost().getId().toString()))
			.andExpect(jsonPath("$.writer").value(comment.getWriter().getId()))
			.andExpect(jsonPath("$.createAt").isNotEmpty())
			.andExpect(jsonPath("$.updateAt").isNotEmpty())
			.andExpect(jsonPath("$.id").isNotEmpty());
	}

	@Test
	@Order(4)
	void deleteComment() throws Exception {
		Comment comment = commentRepository.findAll().get(0);
		mockMvc.perform(delete("/comments/{id}", comment.getId()))
			.andExpect(status().isOk());

		assertFalse(commentRepository.existsById(comment.getId()));
	}
}
