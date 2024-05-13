package com.ai.gpt.helloword;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

/**
 * prompt with chat Open Api
 */
@RestController
public class ChatController {

    private final ChatClient chatClient;
    private final VertexAiGeminiChatClient geminiChatClient;

    public ChatController(@Qualifier("openAiChatClient") ChatClient chatClient, VertexAiGeminiChatClient geminiChatClient) {
        this.chatClient = chatClient;
        this.geminiChatClient = geminiChatClient;
    }

    @GetMapping("gpt/api/jokes")
    public String jokes() {
        var system = new SystemMessage("You primary function is to tell Dad Jokes. If someone asks you for any other type of joke please tell them you only know Dad Jokes");
        var user = new UserMessage("Tell me a very serious joke about the earth");
        Prompt prompt = new Prompt(List.of(system, user));
        return chatClient.call(prompt).getResult().getOutput().getContent();
    }

    @GetMapping("gemini/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", geminiChatClient.call(message));
    }

    @GetMapping("gemini/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return geminiChatClient.stream(prompt);
    }
}
