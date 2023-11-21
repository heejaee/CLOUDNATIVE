package com.example.cloudnative.domain.voter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class AnswerVoterId implements Serializable {

    @Column(name = "answer_id")
    private Long answerId;

    @Column(name = "user_id")
    private Long userId;

    private AnswerVoterId(Long answerId, Long userId) {
        this.answerId = answerId;
        this.userId = userId;
    }

    public static AnswerVoterId of(Long answerId, Long userId) {
        return new AnswerVoterId(answerId, userId);
    }
}
