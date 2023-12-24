package com.example.cloudnative.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.cloudnative.domain.CloudUser;
import com.example.cloudnative.repository.QuestionRepository;
import com.example.cloudnative.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootApplication
@ExtendWith(MockitoExtension.class)
@Transactional
class QuestionServiceTest {

    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionRepository questionRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserService userService;


    @DisplayName("아이디로 이름찾기")
    @Test
    void findById() {
        // given
        //questionRepository.deleteAllInBatch();
//        userService.create("희재", "hjhj@naver.com", "1234");
//        BDDMockito.given(userService.create("희재", "hjhj@naver.com", "1234"))
//                .willReturn(CloudUser.of("희재", "hjhj@naver.com", "1234"));
//
////        // when
////        questionService.create("제목", "내용", user);
////        // then
////        assertThat(questionRepository.findById(0).get().getContent()).isEqualTo("내용");
//        verify(userRepository, times(1)).save(any(CloudUser.class));

    }
}