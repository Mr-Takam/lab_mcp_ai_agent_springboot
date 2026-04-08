package com.example.agent.web;

import com.example.agent.agent.BacklogAgent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AgentControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    // On mock l'agent directement pour éviter d'instancier Claude/Anthropic pendant le test
    @MockBean
    private BacklogAgent backlogAgent;

    @Test
    void should_run_agent_prompt_and_return_response() {
        // Simulation de la réponse de l'IA
        String mockResponse = "I have created the issue for you.";
        when(backlogAgent.handle(anyString())).thenReturn(mockResponse);

        // Test de l'endpoint /api/run
        webTestClient.post()
                .uri("/api/run")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("prompt", "Create a test issue"))
                .exchange()
                .expectStatus().isOk() // Vérifie que le code est 200
                .expectBody(String.class).isEqualTo(mockResponse); // Vérifie le contenu
    }
}