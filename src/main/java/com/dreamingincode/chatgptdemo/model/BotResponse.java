package com.dreamingincode.chatgptdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BotResponse {
    private List<FlashCard> flashCards;
    private double callDuration;
}
