package com.hana4.springexam2.entity;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Post")
public class Post extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@Column(name = "title", nullable = false, length = 255)
	private String title;

	@ManyToOne
	@JoinColumn(name = "writer", nullable = false, foreignKey = @ForeignKey(name = "fk_Post_User"))
	@Comment("작성자 ID")
	private User writer;

	@Column(name = "body", nullable = false, columnDefinition = "TEXT")
	private String body;

	// @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	// private List<com.hana4.springexam2.entity.Comment> comments = new ArrayList<>();

	public Post(String title, User writer, String body) {
		this.title = title;
		this.writer = writer;
		this.body = body;
	}

}

