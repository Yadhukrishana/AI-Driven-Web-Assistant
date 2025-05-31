package com.yadhu.ai_driven_web_assistant.service;

import com.yadhu.ai_driven_web_assistant.dto.ResearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Objects;

@Service
public class ResearchService {

    @Value("${gemini.api.uri}")
    private String gemini_api_url;
    @Value("${gemini.api.key}")
    private String getGemini_api_key;

    private final WebClient webClient;

    public ResearchService(WebClient.Builder webClientBuilder)
    {
        this.webClient=webClientBuilder.build();
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

        return extractTextfromResponse(response);
    }

    private String extractTextfromResponse(String response) {
        try {

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
