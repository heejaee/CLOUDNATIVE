package com.example.cloudnative.repository;

import com.example.cloudnative.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Page<Question> findAll(Pageable pageable);
}
