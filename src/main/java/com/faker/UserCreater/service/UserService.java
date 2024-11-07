package com.faker.UserCreater.service;

import com.faker.UserCreater.entity.User;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class UserService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Faker faker;

	public void generateAndPostUsers(int count) {
		try {
			ExecutorService executor = Executors.newFixedThreadPool(10); // Adjust the number of threads as needed

			for (int i = 0; i < count; i++) {
				executor.submit(this::postRandomUser);
			}

			executor.shutdown();
		} catch (Exception e) {
			log.error("error: ", e);
			throw new RuntimeException(e);
		}

	}

	private void postRandomUser() {
		User user = new User();
		user.setName(faker.name().fullName());
		user.setEmail(faker.internet().emailAddress());
		user.setAddress(faker.address().fullAddress());
		user.setPhone(faker.phoneNumber().phoneNumber());
		user.setCountry(faker.country().name());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<User> request = new HttpEntity<>(user, headers);

		String apiUrl = "http://localhost:8080/user";
		restTemplate.postForEntity(apiUrl, request, String.class);
	}
}
