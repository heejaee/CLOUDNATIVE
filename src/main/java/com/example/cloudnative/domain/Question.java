package com.example.cloudnative.domain;

import com.example.cloudnative.domain.voter.QuestionVoter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private CloudUser author;

    @OneToMany
    private Set<QuestionVoter> voter = new HashSet<>();

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    private Question(String subject, String content, LocalDateTime createDate, CloudUser author) {
        this.subject = subject;
        this.content = content;
        this.createDate = createDate;
        this.author = author;
    }

    public static Question of(String subject, String content, LocalDateTime createDate, CloudUser user){
        return new Question(subject, content, createDate, user);
    }

    public void modify(String subject, String content, LocalDateTime createDate) {
        this.subject = subject;
        this.content = content;
        this.createDate = createDate;
    }

    public boolean isAuthorNameEquals(String userName) {
        return author.getUsername().equals(userName);
    }

    public void addVoter(QuestionVoter questionVoter) {
        voter.add(questionVoter);
    }

    public void plusView(int view) {
        this.view = view+1;
    }
}
