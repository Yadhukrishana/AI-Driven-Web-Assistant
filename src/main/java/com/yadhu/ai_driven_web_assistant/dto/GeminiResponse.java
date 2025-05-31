package com.yadhu.ai_driven_web_assistant.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminiResponse {

    private List<Candidate>candidate;
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Candidate{
        private Content content;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Content{
        private Parts parts;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Parts{
        private String text;
    }
}
