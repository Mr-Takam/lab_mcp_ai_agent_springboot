package com.example.agent;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "github.owner=test",
    "github.repo=test",
    "anthropic.api-key=test"
})
class AgentApplicationTests {

	@Test
	void contextLoads() {
	}

}
