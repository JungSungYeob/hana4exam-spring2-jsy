package com.hana4.springexam2;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.hana4.springexam2.entity.User;
import com.hana4.springexam2.repository.UserRepository;

@Component
public class InitialDataLoader implements ApplicationRunner {
	private final UserRepository userRepository;

	public InitialDataLoader(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// System.out.println("hi");
		for (short n = 1; n <= 5; n++) {
			userRepository.save(new User("Kim" + n, "Kim" + n + "@gmail.com"));
		}
	}
}
