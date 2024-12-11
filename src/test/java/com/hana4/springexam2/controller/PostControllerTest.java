package com.hana4.springexam2.controller;

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
import com.hana4.springexam2.entity.Post;
import com.hana4.springexam2.entity.User;
import com.hana4.springexam2.repository.PostRepository;
import com.hana4.springexam2.repository.UserRepository;

import jakarta.persistence.EntityManager;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)//실행 순서 지정 가능
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;//직렬화, 역질렬화를 위한 클래스

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private EntityManager em;

	@BeforeEach
	public void setUp() {
		// 테스트 실행 전에 Post와 User 데이터를 생성
		User writer = new User("testUser", "test@example.com");
		userRepository.save(writer);

		for (short n = 1; n <= 5; n++) {
			postRepository.save(new Post("Initial Title" + n, writer, "Initial Body" + n));
		}

	}

	@Test
	@Order(2)
	void getAllPosts() throws Exception {
		mockMvc.perform(get("/post").contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$.length()").value(5));
	}

	@Test
	@Order(1)
	void createPostTest() throws Exception {
		User writer = userRepository.findAll().get(0);

		Map<String, String> request = new HashMap<>();
		request.put("title", "Sample Post Title");
		request.put("body", "Sample Post Body");
		request.put("writer", writer.getId());
		mockMvc.perform(post("/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.title").value("Sample Post Title"))
			.andExpect(jsonPath("$.body").value("Sample Post Body"))
			.andExpect(jsonPath("$.writer").value(writer.getId()))
			.andExpect(jsonPath("$.id").isNotEmpty())
			.andExpect(jsonPath("$.createAt").isNotEmpty())
			.andExpect(jsonPath("$.updateAt").isNotEmpty())
			.andDo(print());
	}

	@Test
	@Order(4)
	void updatePostTest() throws Exception {
		Map<String, String> request = new HashMap<>();
		request.put("title", "Update Post Title");
		request.put("body", "Update Post Body");
		mockMvc.perform(patch("/post/{id}", postRepository.findAll().get(0).getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title").value("Update Post Title"))
			.andExpect(jsonPath("$.body").value("Update Post Body"))
			.andExpect(jsonPath("$.writer").isNotEmpty())
			.andExpect(jsonPath("$.id").isNotEmpty())
			.andExpect(jsonPath("$.createAt").isNotEmpty())
			.andExpect(jsonPath("$.updateAt").isNotEmpty());
	}

	@Test
	@Order(3)
	void deletePostTest() throws Exception {
		// postRepository.findAll().get(0)
		mockMvc.perform(delete("/post/{id}", postRepository.findAll().get(0).getId())
			).andExpect(status().isOk())
			.andExpect(jsonPath("$.title").isNotEmpty())
			.andExpect(jsonPath("$.body").isNotEmpty())
			.andExpect(jsonPath("$.writer").isNotEmpty())
			.andExpect(jsonPath("$.id").isNotEmpty())
			.andExpect(jsonPath("$.createAt").isNotEmpty())
			.andExpect(jsonPath("$.updateAt").isNotEmpty());
		;
	}

	@Test
	@Order(5)
	void getPostByIdTest() throws Exception {
		mockMvc.perform(get("/post/{id}", postRepository.findAll().get(0).getId()))
			.andDo(print())
			.andExpect(status().isOk()).andExpect(jsonPath("$.title").value("Initial Title1"))
			.andExpect(jsonPath("$.body").value("Initial Body1")).andExpect(jsonPath("$.writer").isNotEmpty())
			.andExpect(jsonPath("$.id").isNotEmpty())
			.andExpect(jsonPath("$.createAt").isNotEmpty())
			.andExpect(jsonPath("$.updateAt").isNotEmpty());
	}

}
