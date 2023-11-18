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

    public QuestionVoterId(Long questionId, Long userId) {
        this.questionId = questionId;
        this.userId = userId;
    }
}
