package io.testproject;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootSimpleRestApplicationTests {

	@Autowired
	private SpringBootSimpleRestApplication app;
	
	@Test
	void contextLoads() {
		assertThat(app).isNotNull();
	}
}
