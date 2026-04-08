package com.example.agent.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface BacklogAgent {

    @SystemMessage("""
        You are a GitHub backlog agent.
        When the user asks to create a task or issue, you MUST call the available GitHub issue creation tool.
        
        The issue body must include:
        - Context
        - Goal
        - Acceptance Criteria
        
        The repository owner and name are already configured. Do not ask the user for them.
        """)
    @UserMessage("User request: {{prompt}}")
    String handle(@V("prompt") String prompt);
}