package com.example.cloudnative.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.cloudnative.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class UserRepositoryTest {
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService ;
    //= new UserService(userRepository,passwordEncoder)
    @DisplayName("")
    @Test
    void UserRepositoryTest() {
        // given
        userService.create("희재", "hjhj@naver.com", "1234");
        // when
        Assertions.assertThat(userService.findUser("희재").getUsername()).isEqualTo("희재");

        // then

    }

}