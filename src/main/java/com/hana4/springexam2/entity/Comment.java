package com.hana4.springexam2.entity;

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
@Table(name = "Comment")
public class Comment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "post", nullable = false, foreignKey = @ForeignKey(name = "fk_Comment_Post"))
	// @JoinColumn(name = "post", nullable = false)
	private Post post;

	@ManyToOne
	@JoinColumn(name = "writer", nullable = false, foreignKey = @ForeignKey(name = "fk_Comment_User"))
	// @JoinColumn(name = "writer", nullable = false)
	@org.hibernate.annotations.Comment("작성자 ID")
	private User writer;

	@Column(name = "body", nullable = false, length = 500)
	private String body;

	public Comment(String body, Post post, User user) {
		this.body = body;
		this.post = post;
	}
}
