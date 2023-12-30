package com.example.cloudnative.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.cloudnative.domain.CloudUser;
import com.example.cloudnative.domain.Question;
import com.example.cloudnative.repository.QuestionRepository;
import com.example.cloudnative.repository.QuestionVoterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
//@SpringBootTest
@ExtendWith(MockitoExtension.class)
//@Transactional
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionVoterRepository questionVoterRepository;

    @InjectMocks
    private QuestionService questionService;


    @DisplayName("질문 생성하기")
    @Test
    void create() {
        String subject = "제목";
        String content = "내용";
        String username = "john_doe";
        CloudUser mockUser = new CloudUser(username, "john@example.com", "encodedPassword123");
        // mock(CloudUser.class);
        // new CloudUser(username, "john@example.com", "encodedPassword123");

        Question question = questionService.create(subject, content, mockUser);
        //BddMockito
        then(questionRepository).should(atMost(1)).save(question);
        // Mockito
        verify(questionRepository, times(1)).save(any(Question.class));
        verify(questionRepository, times(1)).save(eq(question));

    }

    @DisplayName("질문 수정하기")
    @Test
    void modify() {
        // given
        String subject = "제목";
        String content = "내용";
        String username = "john_doe";
        CloudUser mockUser = new CloudUser(username, "john@example.com", "encodedPassword123");
        // mock(CloudUser.class);
        // new CloudUser(username, "john@example.com", "encodedPassword123");

        Question question = questionService.create(subject, content, mockUser);

        // when
        questionService.modify(question, subject, content);
        // then
        // questionService.create()때 1번 + questionService.modify()때 1번
        verify(questionRepository, times(2)).save(question);
    }

    @DisplayName("")
    @Test
    void delete() {
        // given

        // when

        // then

    }

}