package com.example.cloudnative.repository;

import com.example.cloudnative.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
