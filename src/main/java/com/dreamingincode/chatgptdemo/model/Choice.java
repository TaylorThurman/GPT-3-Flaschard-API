package com.dreamingincode.chatgptdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Choice {
    private Integer index;
    private String text;
    @JsonProperty("finish_reason")
    private String finishReason;
}
