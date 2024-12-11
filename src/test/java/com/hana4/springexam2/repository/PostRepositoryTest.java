package com.hana4.springexam2.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hana4.springexam2.entity.Post;
import com.hana4.springexam2.entity.User;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
public class PostRepositoryTest {
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private EntityManager em;

	@Test
	public void addPostTest() {
		User writer = new User();
		writer.setName("Kim");
		writer.setEmail("kim@naver.com");
		em.persist(writer);

		Post post = new Post();
		post.setTitle("Title Sample");
		post.setBody("Body Sample: sample contents");
		post.setWriter(writer);
		Post savedPost = postRepository.save(post);

		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		assertThat(savedPost.getId()).isNotNull();
		assertThat(savedPost.getTitle()).isEqualTo("Title Sample");
		assertThat(savedPost.getBody()).isEqualTo("Body Sample: sample contents");
		assertThat(savedPost.getWriter()).isEqualTo(writer);
		assertThat(savedPost.getCreateAt()).isNotNull();
		assertThat(savedPost.getCreateAt().format(formatter)).isEqualTo(savedPost.getUpdateAt().format(formatter));
		assertThat(savedPost.getCreateAt().format(formatter)).isEqualTo(now.format(formatter));
	}

	@Test
	public void updatePostTest() {
		User writer = new User();
		writer.setName("Kim");
		writer.setEmail("Kim@naver.com");
		em.persist(writer);

		Post post = new Post();
		post.setTitle("Legacy Post Title");
		post.setBody("Legacy Post Body: Legacy Post Contents");
		post.setWriter(writer);
		Post savedPost = postRepository.save(post);

		Post newPost = postRepository.findById(savedPost.getId()).orElseThrow();
		newPost.setTitle("Updated Post Title");
		newPost.setBody("Updated Post Body: Updated Post Contents");
		Post updatedPost = postRepository.save(newPost);

		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		assertThat(updatedPost.getId()).isNotNull();
		assertThat(updatedPost.getTitle()).isEqualTo("Updated Post Title");
		assertThat(updatedPost.getBody()).isEqualTo("Updated Post Body: Updated Post Contents");
		assertThat(updatedPost.getWriter()).isEqualTo(writer);
		assertThat(updatedPost.getUpdateAt().format(formatter)).isEqualTo(now.format(formatter));
	}

	@Test
	public void deletePostTest() {
		User writer = new User();
		writer.setName("Kim");
		writer.setEmail("kim@naver.com");
		em.persist(writer);

		Post post = new Post();
		post.setTitle("Title Sample");
		post.setBody("Body Sample: sample contents");
		post.setWriter(writer);
		Post savedPost = postRepository.save(post);

		postRepository.delete(savedPost);

		Optional<Post> deletedPost = postRepository.findById(savedPost.getId());
		assertThat(deletedPost).isEmpty();
	}

	@Test
	public void findAllPostsTest() {
		User writer1 = new User();
		writer1.setName("Kim10");
		writer1.setEmail("Kim10@naver.com");
		em.persist(writer1);

		User writer2 = new User();
		writer2.setName("Kim11");
		writer2.setEmail("Kim11@naver.com");
		em.persist(writer2);

		Post post1 = new Post();
		post1.setTitle("Title Sample1");
		post1.setBody("Body Sample1: sample contents1");
		post1.setWriter(writer1);
		Post savedPost1 = postRepository.save(post1);

		Post post2 = new Post();
		post2.setTitle("Title Sample2");
		post2.setBody("Body Sample: sample contents2");
		post2.setWriter(writer2);
		Post savedPost2 = postRepository.save(post2);

		List<Post> posts = postRepository.findAll();

		assertThat(posts).hasSize(2);
		assertThat(posts.get(0).getTitle()).isEqualTo("Title Sample1");
		assertThat(posts.get(0).getWriter()).isEqualTo(writer1);
		assertThat(posts.get(1).getTitle()).isEqualTo("Title Sample2");
		assertThat(posts.get(1).getWriter()).isEqualTo(writer2);
	}

	@Test
	public void getPostTest() {
		User writer = new User();
		writer.setName("Kim10");
		writer.setEmail("Kim10@naver.com");
		em.persist(writer);

		Post post = new Post();
		post.setTitle("Title Sample");
		post.setBody("Body Sample: sample contents");
		post.setWriter(writer);
		Post savedPost = postRepository.save(post);

		Optional<Post> retrievedPost = postRepository.findById(post.getId());

		assertThat(retrievedPost).isPresent();
		assertThat(retrievedPost.get().getTitle()).isEqualTo(post.getTitle());
		assertThat(retrievedPost.get().getBody()).isEqualTo(post.getBody());
		assertThat(retrievedPost.get().getWriter()).isEqualTo(writer);
		assertThat(retrievedPost.get().getWriter().getName()).isEqualTo(writer.getName());
	}
}
