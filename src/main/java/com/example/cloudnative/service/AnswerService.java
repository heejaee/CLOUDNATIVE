package com.example.cloudnative.service;

import com.example.cloudnative.domain.Answer;
import com.example.cloudnative.domain.CloudUser;
import com.example.cloudnative.domain.Question;
import com.example.cloudnative.domain.voter.AnswerVoter;
import com.example.cloudnative.domain.voter.AnswerVoterId;
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

    public Answer create(Question question, String content, CloudUser author) {
        Answer answer = Answer.of(content, LocalDateTime.now(), question, author);
        answerRepository.save(answer);
        return answer;
    }

    public Answer findAnswer(Integer id) {
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.modify(content, LocalDateTime.now());
        answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    public void vote(Answer answer, CloudUser user) {
        // 답변Id와 UserId
        AnswerVoterId answerVoterId = AnswerVoterId.of(answer.getId(), user.getId());
        // 답변Id와 UserId를 복합키로 사용
        AnswerVoter answerVoter = AnswerVoter.from(answerVoterId);
        answerVoterRepository.save(answerVoter);
        // Set에 저장하여 한 답변에는 한사람당 하나의 추천만 가능합니다.
        answer.addVoter(answerVoter);
        answerRepository.save(answer);
    }
}
