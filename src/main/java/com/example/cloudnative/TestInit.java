package com.example.cloudnative;

import com.example.cloudnative.domain.Question;
import com.example.cloudnative.repository.QuestionRepository;
import com.example.cloudnative.service.QuestionService;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestInit {

    private final QuestionRepository questionRepository;

    @PostConstruct
    public void init(){
        Question q1 = new Question();
        q1.setSubject("질문글입니다.");
        q1.setContent("도커란 무엇인가?");
        q1.setCreateDate(LocalDateTime.now());

        Question q2 = new Question();
        q2.setSubject("맨유분석글");
        q2.setContent("텐하흐 종신");
        q2.setCreateDate(LocalDateTime.now());

        questionRepository.save(q1);
        questionRepository.save(q2);
    }
}
