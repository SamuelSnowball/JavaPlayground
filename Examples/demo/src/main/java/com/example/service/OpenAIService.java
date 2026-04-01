package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class OpenAIService {

    private final OpenAIClient client;

    private static final List<String> CATEGORIES = List.of(
        "Fruits",
        "Vegetables",
        "Grains",
        "Proteins",
        "Dairy",
        "Seafood",
        "Meat",
        "Poultry",
        "Legumes",
        "Nuts and Seeds",
        "Herbs and Spices",
        "Baked Goods",
        "Snacks",
        "Beverages",
        "Condiments",
        "Sweets and Desserts",
        "Oils and Fats",
        "Soups and Stews",
        "Pasta and Noodles",
        "Breakfast Foods",
        "Other"
    );

    private static final String PROMPT = """
        You are a food-classification assistant. Your task is to categorize each food item into one or more of the predefined categories below. 
        You must ONLY use the provided categories and no others.
        Output ONLY valid JSON. Do not include explanations or extra text.

        Rules:
        1. Every item must be assigned at least one category.
        2. If an item is unclear, select the "Other" category.
        3. Do NOT invent new categories.
        4. Output the result as a JSON array of objects in the format:
        { "item": "<item>", "categories": ["Meat", "Snacks"]  }
        """;

    /**
     * Categorizes the given food items using the predefined prompt and categories.
     *
     * @param items the food items to categorize
     * @return the model's JSON response
     */
    @Cacheable("categorization")
    public String categorize(List<String> items) {
        String systemMessage = PROMPT + "\n" + "The categories are: " + CATEGORIES + "\n" + "The items that need categorizing are as follows: ";
        String userMessage = items.toString();

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_5_MINI)
                .addSystemMessage(systemMessage)
                .addUserMessage(userMessage)
                .build();

        ChatCompletion completion = client.chat().completions().create(params);

        String reply = completion.choices().get(0).message().content().orElse("");
        log.info("OpenAI reply: {}", reply);
        return reply;
    }


}
