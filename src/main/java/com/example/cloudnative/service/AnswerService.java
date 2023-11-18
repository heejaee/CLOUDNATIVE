package com.example.cloudnative.service;

import com.example.cloudnative.domain.Answer;
import com.example.cloudnative.domain.CloudUser;
import com.example.cloudnative.domain.Question;
import com.example.cloudnative.domain.voter.AnswerVoter;
import com.example.cloudnative.domain.voter.AnswerVoterId;
import com.example.cloudnative.domain.voter.QuestionVoter;
import com.example.cloudnative.domain.voter.QuestionVoterId;
import com.example.cloudnative.repository.AnswerRepository;
import com.example.cloudnative.repository.AnswerVoterRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerVoterRepository answerVoterRepository;

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

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    public void vote(Answer answer, CloudUser user) {
        AnswerVoterId answerVoterId = new AnswerVoterId(answer.getId(), user.getId());
        AnswerVoter answerVoter = new AnswerVoter();
        answerVoter.setAnswerVoterId(answerVoterId);
        answerVoterRepository.save(answerVoter);

        answer.getVoter().add(answerVoter);
        answerRepository.save(answer);
    }
}
