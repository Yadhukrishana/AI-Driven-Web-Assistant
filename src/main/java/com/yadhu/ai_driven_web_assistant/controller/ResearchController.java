package com.yadhu.ai_driven_web_assistant.controller;

import com.yadhu.ai_driven_web_assistant.dto.ResearchRequest;
import com.yadhu.ai_driven_web_assistant.service.ResearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/research")
@AllArgsConstructor
public class ResearchController {

private final ResearchService researchService;
    @PostMapping("/process")
public ResponseEntity<String> ProcessContent(ResearchRequest request)
{
    String result = researchService.processContent(request);
    return ResponseEntity.ok(result);
}
}
