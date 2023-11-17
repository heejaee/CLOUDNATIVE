package com.example.cloudnative.service;

import com.example.cloudnative.domain.Answer;
import com.example.cloudnative.domain.CloudUser;
import com.example.cloudnative.domain.Question;
import com.example.cloudnative.repository.AnswerRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, String content, CloudUser author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        answerRepository.save(answer);
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        answerRepository.save(answer);
    }
}