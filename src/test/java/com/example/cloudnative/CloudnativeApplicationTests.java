package com.example.cloudnative;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.cloudnative.domain.Question;
import com.example.cloudnative.repository.QuestionRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CloudnativeApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void testJpa() {

    }


    @Test
    void testJpa2() {
        List<Question> all = questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }
}
