package com.example.cloudnative.domain.voter;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerVoter {

    @EmbeddedId
    private AnswerVoterId answerVoterId;

    private AnswerVoter(AnswerVoterId answerVoterId) {
        this.answerVoterId = answerVoterId;
    }

    public static AnswerVoter from(AnswerVoterId answerVoterId) {
        return new AnswerVoter(answerVoterId);
    }
}
