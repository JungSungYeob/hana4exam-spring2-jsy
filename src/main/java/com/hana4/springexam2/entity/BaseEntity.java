package com.hana4.springexam2.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
	@CreationTimestamp
	@Column(name = "createAt", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
	@ColumnDefault("CURRENT_TIMESTAMP")
	@Comment("등록일시")
	private LocalDateTime createAt;

	@UpdateTimestamp
	@Column(name = "updateAt", nullable = false, columnDefinition = "TIMESTAMP")
	@ColumnDefault("CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Comment("수정일시")
	private LocalDateTime updateAt;

	// @PrePersist
	// public void prePersist() {
	// 	this.createAt = LocalDateTime.now();
	// 	this.updateAt = LocalDateTime.now();
	// }
	//
	// @PreUpdate
	// public void preUpdate() {
	// 	this.updateAt = LocalDateTime.now();
	// }
}
