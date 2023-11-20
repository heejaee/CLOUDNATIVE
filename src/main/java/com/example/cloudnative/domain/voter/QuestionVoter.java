package com.example.cloudnative.domain.voter;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionVoter {
    @EmbeddedId
    private QuestionVoterId questionVoterId;

    private QuestionVoter(QuestionVoterId questionVoterId) {
        this.questionVoterId = questionVoterId;
    }

    public static QuestionVoter from(QuestionVoterId id) {
        return new QuestionVoter(id);
    }
}
