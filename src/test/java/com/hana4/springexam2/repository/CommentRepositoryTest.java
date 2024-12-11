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

import com.hana4.springexam2.entity.Comment;
import com.hana4.springexam2.entity.Post;
import com.hana4.springexam2.entity.User;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
public class CommentRepositoryTest {
	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private EntityManager em;

	@Test
	public void addCommentTest() {
		User writer = new User();
		writer.setName("Kim");
		writer.setEmail("kim@naver.com");
		em.persist(writer);

		Post post = new Post();
		post.setTitle("Title Sample");
		post.setBody("Body Sample: sample contents");
		post.setWriter(writer);
		em.persist(post);

		Comment comment = new Comment();
		comment.setPost(post);
		comment.setWriter(writer);
		comment.setBody("Body Sample: sample contents");
		Comment savedComment = commentRepository.save(comment);

		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		assertThat(savedComment.getId()).isNotNull();
		assertThat(savedComment.getBody()).isEqualTo("Body Sample: sample contents");
		assertThat(savedComment.getWriter()).isEqualTo(writer);
		assertThat(savedComment.getCreateAt()).isNotNull();
		assertThat(savedComment.getUpdateAt()).isNotNull();
		assertThat(savedComment.getCreateAt().format(formatter)).isEqualTo(now.format(formatter));
		assertThat(savedComment.getCreateAt().format(formatter)).isEqualTo(
			savedComment.getUpdateAt().format(formatter));
		assertThat(savedComment.getPost()).isEqualTo(post);
	}

	@Test
	public void updateCommentTest() {
		User writer = new User();
		writer.setName("Kim");
		writer.setEmail("kim@naver.com");
		em.persist(writer);

		Post post = new Post();
		post.setTitle("Title Sample");
		post.setBody("Body Sample: sample contents");
		post.setWriter(writer);
		em.persist(post);

		Comment comment = new Comment();
		comment.setPost(post);
		comment.setWriter(writer);
		comment.setBody("Legacy Body Sample: Legacy sample contents");
		Comment savedComment = commentRepository.save(comment);

		Comment newComment = commentRepository.findById(savedComment.getId()).orElseThrow();
		newComment.setBody("Updated Body Sample: Updated sample contents");
		Comment updatedComment = commentRepository.save(newComment);

		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		assertThat(updatedComment.getId()).isNotNull();
		assertThat(updatedComment.getBody()).isEqualTo("Updated Body Sample: Updated sample contents");
		assertThat(updatedComment.getUpdateAt().format(formatter)).isEqualTo(now.format(formatter));
		assertThat(updatedComment.getWriter()).isEqualTo(writer);
		assertThat(updatedComment.getPost()).isEqualTo(post);
	}

	@Test
	public void deleteCommentTest() {
		User writer = new User();
		writer.setName("Kim");
		writer.setEmail("kim@naver.com");
		em.persist(writer);

		Post post = new Post();
		post.setTitle("Title Sample");
		post.setBody("Body Sample: sample contents");
		post.setWriter(writer);
		em.persist(post);

		Comment comment = new Comment();
		comment.setPost(post);
		comment.setWriter(writer);
		comment.setBody("Body Sample: sample contents");
		Comment savedComment = commentRepository.save(comment);

		commentRepository.deleteById(savedComment.getId());

		Optional<Comment> deleteComment = commentRepository.findById(savedComment.getId());
		assertThat(deleteComment).isEmpty();
	}

	@Test
	public void findAllCommentsTest() {
		User writer = new User();
		writer.setName("Kim");
		writer.setEmail("kim@naver.com");
		em.persist(writer);

		Post post = new Post();
		post.setTitle("Title Sample");
		post.setBody("Body Sample: sample contents");
		post.setWriter(writer);
		em.persist(post);

		Comment comment1 = new Comment();
		comment1.setPost(post);
		comment1.setWriter(writer);
		comment1.setBody("Body Sample1: sample contents");
		Comment savedComment1 = commentRepository.save(comment1);

		Comment comment2 = new Comment();
		comment2.setPost(post);
		comment2.setWriter(writer);
		comment2.setBody("Body Sample2: sample contents");
		Comment savedComment2 = commentRepository.save(comment2);

		List<Comment> comments = commentRepository.findAll();

		assertThat(comments).hasSize(2);
		assertThat(comments.get(0)).isEqualTo(savedComment1);
		assertThat(comments.get(1)).isEqualTo(savedComment2);
		assertThat(comments.get(0).getBody()).isEqualTo("Body Sample1: sample contents");
		assertThat(comments.get(1).getBody()).isEqualTo("Body Sample2: sample contents");
		assertThat(comments.get(0).getWriter()).isEqualTo(writer);
		assertThat(comments.get(1).getWriter()).isEqualTo(writer);
	}
}
