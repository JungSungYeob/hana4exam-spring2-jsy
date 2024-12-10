package com.hana4.springexam2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "User", uniqueConstraints = {@UniqueConstraint(name = "user_name_unique", columnNames = {"name"})})
public class User {
	@Id
	@Column(name = "id", length = 36, nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "name", nullable = false, length = 31, unique = true)
	private String name;

	@Column(name = "email", nullable = true, length = 255)
	private String email;
}
