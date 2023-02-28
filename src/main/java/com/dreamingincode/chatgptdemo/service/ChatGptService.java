package com.dreamingincode.chatgptdemo.service;

import com.dreamingincode.chatgptdemo.config.ChatGptConfig;
import com.dreamingincode.chatgptdemo.model.BotRequest;
import com.dreamingincode.chatgptdemo.model.ChatGptRequest;
import com.dreamingincode.chatgptdemo.model.ChatGptResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class ChatGptService {

    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${API-KEY}")
    private String API_KEY;

    //    Build headers
    public HttpEntity<ChatGptRequest> buildHttpEntity(ChatGptRequest chatRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + API_KEY);
        return new HttpEntity<>(chatRequest, headers);
    }

    //    Generate response
    public ChatGptResponse getResponse(HttpEntity<ChatGptRequest> chatRequestHttpEntity) {
        ResponseEntity<ChatGptResponse> responseEntity = restTemplate.postForEntity(
                ChatGptConfig.URL,
                chatRequestHttpEntity,
                ChatGptResponse.class);

        return responseEntity.getBody();
    }

    @Async
    public CompletableFuture<ChatGptResponse> getFlashCardFromApiAsync(BotRequest request, String message) {
        return CompletableFuture.completedFuture(getFlashCardFromApi(request, message));
    }

    public ChatGptResponse getFlashCardFromApi(BotRequest request, String message) {
        return getResponse(
                buildHttpEntity(
                        new ChatGptRequest(
                                request.getModel(),
                                message,
                                request.getTemp(),
                                request.getMaxTokens(),
                                request.getTopP())));
    }
}
