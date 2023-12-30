package com.example.cloudnative.service;

import com.example.cloudnative.domain.CloudUser;
import com.example.cloudnative.domain.Question;
import com.example.cloudnative.domain.voter.QuestionVoter;
import com.example.cloudnative.domain.voter.QuestionVoterId;
import com.example.cloudnative.repository.QuestionRepository;
import com.example.cloudnative.repository.QuestionVoterRepository;
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
    private final QuestionVoterRepository questionVoterRepository;

    public Page<Question> findBySort(int page, String kw, String sort) {
        Pageable pageable;

        if(sort.equals("recommend")){ // 추천순
            pageable = PageRequest.of(page, 10);
            return questionRepository.findAllByVoter(kw, pageable);
        }
        else if(sort.equals("popular")){ // 조회순
            pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "view"));
            return questionRepository.findAllByKeyword(kw, pageable);
        }
        else if(sort.equals("answer")){ // 댓글순
            pageable = PageRequest.of(page, 10);
            return questionRepository.findAllByAnswer(kw, pageable);
        }
        // 최신순
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC,"createDate"));
        return questionRepository.findAllByKeyword(kw, pageable);
    }

    public Question findQuestion(Integer id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public Question create(String subject, String content, CloudUser user) {
        Question q = Question.of(subject, content, LocalDateTime.now(), user);
        questionRepository.save(q);
        return q;
    }

    public void modify(Question question, String subject, String content) {
        question.modify(subject, content, LocalDateTime.now());
        questionRepository.save(question);
    }

    public void delete(Question question) {
       questionRepository.delete(question);
    }

    public void vote(Question question, CloudUser user) {
        // 질문Id와 UserId
        QuestionVoterId questionVoterId = QuestionVoterId.of(question.getId(), user.getId());
        // 질문Id와 UserId를 복합키로 사용
        QuestionVoter questionVoter = QuestionVoter.from(questionVoterId);
        questionVoterRepository.save(questionVoter);
        // Set에 저장하여 한 질문에는 한사람당 하나의 추천만 가능합니다.
        question.addVoter(questionVoter);
        questionRepository.save(question);
    }

    public void plusView(Question question) {
        question.plusView(question.getView());
        questionRepository.save(question);
    }
}
