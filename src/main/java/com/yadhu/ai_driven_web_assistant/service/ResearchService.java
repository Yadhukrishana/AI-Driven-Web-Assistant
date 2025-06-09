package com.yadhu.ai_driven_web_assistant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yadhu.ai_driven_web_assistant.dto.GeminiResponse;
import com.yadhu.ai_driven_web_assistant.dto.ResearchRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service
public class ResearchService {

    @Value("${gemini.api.uri}")
    private String gemini_api_url;
    @Value("${gemini.api.key}")
    private String getGemini_api_key;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public ResearchService(WebClient.@NotNull  Builder webClientBuilder , ObjectMapper objectMapper)
    {

        this.webClient=webClientBuilder.build();
        this.objectMapper=objectMapper;
    }
    public String processContent(ResearchRequest request){

        //        build the prompt
        String prompt = buildPrompt(request);
        Map<String, Object> requestBody = Map.of(
                "contents" , new Object[]
                {
                        Map.of("parts",new Object[]{
                                Map.of("text",prompt)
                        })

                });

        String response = webClient.post()
                .uri(gemini_api_url+getGemini_api_key)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return extractTextFromResponse(response);
    }

    private String extractTextFromResponse(String response) {
        try {
            GeminiResponse geminiResponse = objectMapper.readValue(response, GeminiResponse.class);
            if(geminiResponse.getCandidate()!=null && !geminiResponse.getCandidate().isEmpty()) {
                GeminiResponse.Candidate firstCandidate = geminiResponse.getCandidate().get(0);
                if(firstCandidate.getContent() != null &&
                        firstCandidate.getContent().getParts() != null &&
                        !firstCandidate.getContent().getParts().getText().isEmpty())
                {
                return firstCandidate.getContent().getParts().getText();
                }
            }
            return "no content found in the response";
        }catch (Exception e)
        {
            return "error parsing " + e.getMessage();
        }

    }

    private String buildPrompt(ResearchRequest request)
    {
     StringBuilder prompt = new StringBuilder();
     switch(request.getOperation())
        {
            case "summarise":
                prompt.append("summarize clearly the following ");
                break;
            case "what is ":
                prompt.append("explain the following :\n ");
                break;
            default:
                throw new IllegalArgumentException("unknown operation");
        }
        prompt.append(request.getContent());
     return prompt.toString();
    }



}
