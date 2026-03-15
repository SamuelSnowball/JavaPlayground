package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * Service that wraps the official OpenAI Java SDK.
 *
 * <p>The API key is loaded from {@code application.properties} via the
 * {@code openai.api.key} property, which in turn reads the {@code OPENAI_API_KEY}
 * environment variable (set by your .env file / IDE run configuration).
 *
 * <p>Usage example:
 * <pre>{@code
 *   String reply = openAiService.chat("Explain jOOQ in one sentence.");
 *   System.out.println(reply);
 * }</pre>
 */
@Service
@Slf4j
public class OpenAiService {

    @Value("${openai.api.key}")
    private String apiKey;

    private OpenAIClient client;

    @PostConstruct
    public void init() {
        client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
        log.info("OpenAI client initialised");
    }

    /**
     * Sends a single user message to the GPT-5.1-mini model and returns the
     * assistant's reply as a plain string.
     *
     * @param userMessage the message to send
     * @return the model's text response
     */
    public String chat(String userMessage) {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_5_1_MINI)
                .addUserMessage(userMessage)
                .build();

        ChatCompletion completion = client.chat().completions().create(params);

        String reply = completion.choices().get(0).message().content().orElse("");
        log.info("OpenAI reply: {}", reply);
        return reply;
    }

    /**
     * Sends a conversation with a system prompt and a user message to the model.
     *
     * @param systemPrompt context / persona for the assistant
     * @param userMessage  the actual user question
     * @return the model's text response
     */
    public String chat(String systemPrompt, String userMessage) {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_5_1_MINI)
                .addSystemMessage(systemPrompt)
                .addUserMessage(userMessage)
                .build();

        ChatCompletion completion = client.chat().completions().create(params);

        String reply = completion.choices().get(0).message().content().orElse("");
        log.info("OpenAI reply: {}", reply);
        return reply;
    }
}
