package com.example.cloudnative.repository;

import com.example.cloudnative.domain.QuestionVoter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionVoterRepository extends JpaRepository<QuestionVoter, Integer> {
}
