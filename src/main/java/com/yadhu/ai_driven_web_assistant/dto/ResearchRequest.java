package com.yadhu.ai_driven_web_assistant.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class ResearchRequest {
    private String content;
    private String operation;
}
