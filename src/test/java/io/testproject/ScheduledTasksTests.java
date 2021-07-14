package io.testproject;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import io.testproject.service.TimerService;

@SpringBootTest
public class ScheduledTasksTests {
	
	@SpyBean
	TimerService timer;
	
	@Test
	public void getCryptoCurrencies() { 
		
		doNothing().when(timer).getCryptoCurrencies();
		
		await().atMost(100, TimeUnit.SECONDS).untilAsserted(() -> { 
			verify(timer, atLeast(10)).getCryptoCurrencies();
		});
	}
}
