package com.example.cloudnative.domain.voter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class QuestionVoterId implements Serializable {

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "user_id")
    private Long userId;

    private QuestionVoterId(Long questionId, Long userId) {
        this.questionId = questionId;
        this.userId = userId;
    }

    public static QuestionVoterId of(Long questionId, Long userId) {
        return new QuestionVoterId(questionId, userId);
    }
}
