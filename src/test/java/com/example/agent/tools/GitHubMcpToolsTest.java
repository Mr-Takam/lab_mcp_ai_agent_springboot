package com.example.agent.tools;

import com.example.agent.mcp.McpHttpClient;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GitHubMcpToolsTest {

    @Test
    void should_call_mcp_tool_to_create_issue() {
        // On crée un faux client MCP (Mock)
        McpHttpClient mcpClient = mock(McpHttpClient.class);
        
        // On définit un comportement de test : si on appelle create_issue, répond avec un succès fictif
        when(mcpClient.callTool(eq("create_issue"), anyMap()))
            .thenReturn(Mono.just(Map.of("number", 1, "html_url", "http://github.com/mock/repo/issues/1")));

        GitHubMcpTools tools = new GitHubMcpTools(mcpClient, "owner", "repo");

        // Exécution de l'outil
        String result = tools.createIssue("Titre Test", "Corps du test");

        // Vérification : le résultat doit contenir le message de succès
        assertTrue(result.contains("Issue created successfully"));
        
        // On vérifie que le client MCP a bien été appelé une seule fois
        verify(mcpClient, times(1)).callTool(eq("create_issue"), anyMap());
    }
}