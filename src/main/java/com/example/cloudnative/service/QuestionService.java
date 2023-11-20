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

    public Page<Question> getList(int page, String kw, String sort) {
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

    public void create(String subject, String content, CloudUser user) {
        Question q = Question.of(subject, content, LocalDateTime.now(), user);
        questionRepository.save(q);
    }

    public void modify(Question question, String subject, String content) {
        question.modify(subject, content, LocalDateTime.now());
        questionRepository.save(question);
    }

    public void delete(Question question) {
       questionRepository.delete(question);
    }

    public void vote(Question question, CloudUser user) {
        QuestionVoterId questionVoterId = QuestionVoterId.of(question.getId(), user.getId());
        QuestionVoter questionVoter = QuestionVoter.from(questionVoterId);
        questionVoterRepository.save(questionVoter);

        question.addVoter(questionVoter);
        questionRepository.save(question);
    }

    public void plusView(Question question) {
        question.setView(question.getView()+1);
        questionRepository.save(question);
    }
}
