package com.example.cloudnative.service;

import com.example.cloudnative.domain.Question;
import com.example.cloudnative.repository.QuestionRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC,"createDate"));
        return questionRepository.findAll(pageable);
    }
    public Question getQuestion(Integer id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);
    }
}