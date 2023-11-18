package com.example.cloudnative.domain.voter;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class QuestionVoter {

    @EmbeddedId
    private QuestionVoterId questionVoterId;
}
