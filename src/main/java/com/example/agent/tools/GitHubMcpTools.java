package com.example.agent.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import com.example.agent.mcp.McpHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GitHubMcpTools implements AgentTool {

    private final McpHttpClient mcpClient;
    private final String owner;
    private final String repo;

    public GitHubMcpTools(McpHttpClient mcpClient,
                          @Value("${github.owner}") String owner,
                          @Value("${github.repo}") String repo) {
        this.mcpClient = mcpClient;
        this.owner = owner;
        this.repo = repo;
    }

    @Tool("Create a GitHub issue in the configured repository. Use when the user asks to create a task or issue.")
    public String createIssue(
            @P("The title of the issue") String title,
            @P("The body content of the issue in Markdown") String body
    ) {
        Map result = mcpClient.callTool("create_issue", Map.of(
                "owner", owner,
                "repo", repo,
                "title", title,
                "body", body
        )).block(); // On bloque ici car LangChain4j attend une réponse synchrone du Tool

        return "Issue created successfully: " + result;
    }
}