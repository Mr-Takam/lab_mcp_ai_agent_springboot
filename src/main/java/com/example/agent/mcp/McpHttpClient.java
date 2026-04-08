package com.example.agent.mcp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@Component
public class McpHttpClient {
    private final WebClient webClient;
    private final String path;

    public McpHttpClient(WebClient.Builder builder,
                       @Value("${mcp.base-url}") String baseUrl,
                       @Value("${mcp.path}") String path) {
        this.webClient = builder.baseUrl(baseUrl).build();
        this.path = path;
    }

    public Mono<Map> callTool(String toolName, Map<String, Object> arguments) {
        // Construction du payload JSON-RPC 2.0
        Map<String, Object> payload = Map.of(
            "jsonrpc", "2.0",
            "id", UUID.randomUUID().toString(),
            "method", "tools/call",
            "params", Map.of(
                "name", toolName,
                "arguments", arguments
            )
        );

        return webClient.post()
            .uri(path)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(payload)
            .retrieve()
            .bodyToMono(Map.class)
            .map(response -> {
                if (response.containsKey("error")) {
                    throw new RuntimeException("MCP Error: " + response.get("error"));
                }
                return (Map) response.get("result");
            });
    }
}