package com.example.cloudnative.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.cloudnative.domain.CloudUser;
import com.example.cloudnative.domain.Question;
import com.example.cloudnative.domain.voter.QuestionVoter;
import com.example.cloudnative.repository.QuestionRepository;
import com.example.cloudnative.repository.QuestionVoterRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
class QuestionServiceTest {

    @MockBean
    private QuestionRepository questionRepository;

    @MockBean
    private QuestionVoterRepository questionVoterRepository;

    @Autowired
    private QuestionService questionService;
    @DisplayName("질문 생성하기")
    @Test
    void create() {
        String subject = "제목";
        String content = "내용";
        String username = "john_doe";
        CloudUser mockUser = mock(CloudUser.class);
        // mock(CloudUser.class);
        // new CloudUser(username, "john@example.com", "encodedPassword123");

        //when(questionRepository.save(any(Question.class))).thenReturn(null);
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

    @DisplayName("질문 삭제하기")
    @Test
    void delete() {
        // given
        String subject = "제목";
        String content = "내용";
        String username = "john_doe";
        CloudUser mockUser = new CloudUser(username, "john@example.com", "encodedPassword123");
        // mock(CloudUser.class);
        // new CloudUser(username, "john@example.com", "encodedPassword123");

        Question question = questionService.create(subject, content, mockUser);
        // when
        questionService.delete(question);
        // then
        verify(questionRepository, times(1)).delete(question);
    }
    
    @DisplayName("투표하기")
    @Test
    void vote() {
        // given
        CloudUser user = mock(CloudUser.class);
        Question question = mock(Question.class);
        QuestionVoter questionVoter = questionService.createQuestionVoter(question, user);

        // when
        questionService.vote(question, questionVoter);

        // then
        verify(question, times(1)).addVoter(questionVoter);
        verify(questionVoterRepository, times(1)).save(questionVoter);
        verify(questionRepository, times(1)).save(question);
    }

}