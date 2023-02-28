package com.dreamingincode.chatgptdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlashCard {
    private String question;
    private String answer;
}
