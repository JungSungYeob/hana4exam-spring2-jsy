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
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.title").value("Sample Post Title"))
			.andExpect(jsonPath("$.body").value("Sample Post Body"))
			.andExpect(jsonPath("$.writer").value(writer.getId())).andDo(print());
	}

	@Test
	@Order(4)
	void updatePostTest() throws Exception {
		Map<String, String> request = new HashMap<>();
		System.out.println("hi");
		request.put("title", "Update Post Title");
		request.put("body", "Update Post Body");
		mockMvc.perform(patch("/post/{id}", postRepository.findAll().get(0).getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title").value("Update Post Title"))
			.andExpect(jsonPath("$.body").value("Update Post Body"));
	}

	@Test
	@Order(3)
	void deletePostTest() throws Exception {
		// postRepository.findAll().get(0)
		mockMvc.perform(delete("/post/{id}", postRepository.findAll().get(0).getId())
		).andExpect(status().isOk());
	}

	@Test
	@Order(5)
	void getPostByIdTest() throws Exception {
		mockMvc.perform(get("/post/{id}", postRepository.findAll().get(0).getId()))
			.andDo(print())
			.andExpect(status().isOk()).andExpect(jsonPath("$.title").value("Initial Title1"))
			.andExpect(jsonPath("$.body").value("Initial Body1"));
	}

}

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.mockito.BDDMockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
//
// import java.time.LocalDateTime;
// import java.util.HashMap;
// import java.util.Map;
//
// //@DataJpaTest
// //@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// @SpringBootTest
// @AutoConfigureMockMvc
// @WebMvcTest(PostController.class)
// public class PostControllerTest {
// 	@Autowired
// 	private MockMvc mockMvc;
// 	@Autowired
// 	private ObjectMapper objectMapper; // JSON 변환용
//
// 	@MockBean
// 	private PostService service;
// 	@Test
// 	void getPostTest() throws Exception {
// 		final Long ID = 20241210L;
// 		final LocalDateTime now = LocalDateTime.now();
//
// 		final PostDTO dto = new PostDTO(ID,now, now, "title", "writer",  "body");
//
// 		BDDMockito.given(service.getPost(ID)).willReturn(dto);
//
// 		final String url = "/posts/" + ID;
// 		mockMvc.perform(get(url))
// 			.andExpect(status().isOk())
// 			.andExpect(jsonPath("$.id").exists())
// 			.andExpect(jsonPath("$.title").value("title"))
// 			.andExpect(jsonPath("$.writer").value("writer"))
// 			.andDo(print());
//
// 	}
// 	@Test
// 	@DisplayName("Post 쓰기 테스트")
// 	public void createPostTest() throws Exception {
// 		Map<String, String> request = new HashMap<>();
// 		request.put("title", "Test Post");
// 		request.put("content", "This is a test post.");
// 		request.put("writer", "TestUser");
//
// 		mockMvc.perform(post("/posts")
// 				.contentType(MediaType.APPLICATION_JSON)
// 				.content(objectMapper.writeValueAsString(request)))
// 			.andExpect(status().isCreated())
// 			.andExpect(jsonPath("$.title").value("Test Post"))
// 			.andExpect(jsonPath("$.writer").value("TestUser"));
// 	}
// 	@Test
// 	@DisplayName("Post 수정 테스트")
// 	public void updatePostTest() throws Exception {
// 		Map<String, String> request = new HashMap<>();
// 		request.put("title", "Updated Post");
// 		request.put("content", "This is an updated test post.");
//
// 		mockMvc.perform(put("/posts/{postId}", 1)
// 				.contentType(MediaType.APPLICATION_JSON)
// 				.content(objectMapper.writeValueAsString(request)))
// 			.andExpect(status().isOk())
// 			.andExpect(jsonPath("$.title").value("Updated Post"));
// 	}
// 	@Test
// 	@DisplayName("Post 삭제 테스트")
// 	public void deletePostTest() throws Exception {
// 		mockMvc.perform(delete("/posts/{postId}", 1))
// 			.andExpect(status().isNoContent());
// 	}
// 	@Test
// 	@DisplayName("Post 목록 보기 테스트")
// 	public void listPostsTest() throws Exception {
// 		mockMvc.perform(get("/posts"))
// 			.andExpect(status().isOk())
// 			.andExpect(jsonPath("$").isArray());
// 	}
//
// 	@Test
// 	@DisplayName("Post 상세 보기 테스트")
// 	public void getPostDetailTest() throws Exception {
// 		mockMvc.perform(get("/posts/{postId}", 1))
// 			.andExpect(status().isOk())
// 			.andExpect(jsonPath("$.id").value(1));
// 	}
// 	@Test
// 	@DisplayName("댓글 쓰기 테스트")
// 	public void createCommentTest() throws Exception {
// 		Map<String, String> request = new HashMap<>();
// 		request.put("content", "This is a test comment.");
// 		request.put("writer", "CommentUser");
//
// 		mockMvc.perform(post("/posts/{postId}/comments", 1)
// 				.contentType(MediaType.APPLICATION_JSON)
// 				.content(objectMapper.writeValueAsString(request)))
// 			.andExpect(status().isCreated())
// 			.andExpect(jsonPath("$.content").value("This is a test comment."));
// 	}
//
// 	@Test
// 	@DisplayName("댓글 목록 보기 테스트")
// 	public void listCommentsTest() throws Exception {
// 		mockMvc.perform(get("/posts/{postId}/comments", 1))
// 			.andExpect(status().isOk())
// 			.andExpect(jsonPath("$").isArray());
// 	}
//
// 	@Test
// 	@DisplayName("댓글 수정 테스트")
// 	public void updateCommentTest() throws Exception {
// 		Map<String, String> request = new HashMap<>();
// 		request.put("content", "Updated comment content.");
//
// 		mockMvc.perform(put("/posts/{postId}/comments/{commentId}", 1, 1)
// 				.contentType(MediaType.APPLICATION_JSON)
// 				.content(objectMapper.writeValueAsString(request)))
// 			.andExpect(status().isOk())
// 			.andExpect(jsonPath("$.content").value("Updated comment content."));
// 	}
//
// 	@Test
// 	@DisplayName("댓글 삭제 테스트")
// 	public void deleteCommentTest() throws Exception {
// 		mockMvc.perform(delete("/posts/{postId}/comments/{commentId}", 1, 1))
// 			.andExpect(status().isNoContent());
// 	}
// }
