package com.example.cloudnative.repository;

import com.example.cloudnative.domain.voter.AnswerVoter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerVoterRepository extends JpaRepository<AnswerVoter, Integer> {
}
