package com.example.cloudnative.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class QuestionVoter {

    @EmbeddedId
    private QuestionVoterId questionVoterId;

//    @MapsId
//    @ManyToOne
//    private Question question;
//
//    @MapsId
//    @ManyToOne
//    private CloudUser user;
}
