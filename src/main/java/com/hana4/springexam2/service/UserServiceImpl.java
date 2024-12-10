package com.hana4.springexam2.service;

import org.springframework.stereotype.Service;

import com.hana4.springexam2.dao.UserDAO;

@Service
public class UserServiceImpl implements UserService {
	private UserDAO userDAO;

	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
}
