package com.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void printTable() {
		int tableNum = 2;
		for (int i =1; i<=10; i++) {
			System.out.println(tableNum*i);
		}
	}

}
