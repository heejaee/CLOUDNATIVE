package com.example.cloudnative.util;

import com.example.cloudnative.domain.Question;
import com.example.cloudnative.repository.QuestionRepository;
import com.example.cloudnative.service.QuestionService;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionInit {

    private final QuestionService questionService;

    @PostConstruct
    public void init(){
        // 질문 초기값 설정
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용무";
            questionService.create(subject, content, null);
        }
    }
}
