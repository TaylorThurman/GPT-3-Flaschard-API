package com.dreamingincode.chatgptdemo.controller;

import com.dreamingincode.chatgptdemo.model.BotRequest;
import com.dreamingincode.chatgptdemo.model.BotResponse;
import com.dreamingincode.chatgptdemo.service.FlashCardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flash-cards")
@RequiredArgsConstructor
public class FlashCardsRest {

    private final FlashCardsService flashCardsService;

    @PostMapping("/sync")
    public ResponseEntity<BotResponse> createFlashCardsSync(@RequestBody BotRequest request) {
        return ResponseEntity.ok(flashCardsService.createFlashCardsSync(request));
    }

    @PostMapping("/async")
    public ResponseEntity<BotResponse> createFlashCardsAsync(@RequestBody BotRequest request) {
        return ResponseEntity.ok(flashCardsService.createFlashCardsAsync(request));
    }
}
