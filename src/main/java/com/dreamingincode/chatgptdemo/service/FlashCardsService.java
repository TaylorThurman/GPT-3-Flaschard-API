package com.dreamingincode.chatgptdemo.service;

import com.dreamingincode.chatgptdemo.model.BotRequest;
import com.dreamingincode.chatgptdemo.model.BotResponse;
import com.dreamingincode.chatgptdemo.model.ChatGptResponse;
import com.dreamingincode.chatgptdemo.model.FlashCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FlashCardsService {

    private final ChatGptService chatGptService;

    public BotResponse createFlashCardsAsync(BotRequest botRequest) {
        String[] questionArray = separateQuestions(botRequest.getMessage());
        List<CompletableFuture<ChatGptResponse>> flashcardsFutures = new LinkedList<>();

        long startTime = System.currentTimeMillis();

        for (String question : questionArray) {
            String prompt = String.format("What is a good flash card answer to the question '%s'? Provide an analogy along with definition so it may be better understood. " +
                            "Make the answer a minimum of 5 sentences.",
                    question);
            CompletableFuture<ChatGptResponse> flashcard = chatGptService.getFlashCardFromApiAsync(botRequest, prompt);
            flashcardsFutures.add(flashcard);
        }

        CompletableFuture<List<ChatGptResponse>> futureResponses = allOf(flashcardsFutures);
        List<ChatGptResponse> response = futureResponses.join();

        long endTime = System.currentTimeMillis();
        double duration = ((endTime - startTime) / 1000.00);

        List<FlashCard> flashCards = new ArrayList<>();
        for (int i = 0; i < response.size(); i++) {
            flashCards.add(new FlashCard(questionArray[i], response.get(i).getChoices().get(0).getText()));
        }

        return new BotResponse(flashCards, duration);
    }

    public BotResponse createFlashCardsSync(BotRequest botRequest) {
        String[] questionArray = separateQuestions(botRequest.getMessage());
        List<FlashCard> flashCards = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (String question : questionArray) {
            String prompt = String.format("What is a good flash card answer to the question '%s'? Provide an analogy along with definition so it may be better understood. " +
                            "Make the answer a minimum of 5 sentences.",
                    question);
            ChatGptResponse flashcard = chatGptService.getFlashCardFromApi(botRequest, prompt);
            FlashCard flashCard = new FlashCard(question, flashcard.getChoices().get(0).getText());
            flashCards.add(flashCard);
        }

        long endTime = System.currentTimeMillis();
        double duration = ((endTime - startTime) / 1000.00);

        return new BotResponse(flashCards, duration);
    }

    public <T> CompletableFuture<List<T>> allOf(List<CompletableFuture<T>> futuresList) {
        CompletableFuture<Void> allFuturesResult =
                CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[futuresList.size()]));
        return allFuturesResult.thenApply(v ->
                futuresList.stream().
                        map(CompletableFuture::join).
                        collect(Collectors.<T>toList())
        );
    }

    private String[] separateQuestions(String questionString) {
        return questionString.split(",");
    }
}
