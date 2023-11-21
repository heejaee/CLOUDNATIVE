package com.example.cloudnative.domain;

import com.example.cloudnative.domain.voter.AnswerVoter;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;

    @ManyToOne
    private CloudUser author;

    @OneToMany
    private Set<AnswerVoter> voter;

    private Answer(String content, LocalDateTime createDate, Question question, CloudUser author) {
        this.content = content;
        this.createDate = createDate;
        this.question = question;
        this.author = author;
    }

    public static Answer of(String content, LocalDateTime localDateTime, Question question, CloudUser author) {
        return new Answer(content, localDateTime, question, author);
    }

    public boolean isAuthorNameEquals(String userName) {
        return author.getUsername().equals(userName);
    }

    public void modify(String content, LocalDateTime createDate) {
        this.content = content;
        this.createDate =createDate;
    }

    public void addVoter(AnswerVoter answerVoter) {
        voter.add(answerVoter);
    }
}