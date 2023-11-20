package com.example.cloudnative.repository;

import com.example.cloudnative.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Page<Question> findAll(Pageable pageable);

    @Query("select "
            + "distinct q "
            + "from Question q "
            + "left outer join CloudUser u1 on q.author=u1 "
            + "left outer join Answer a on a.question=q "
            + "left outer join CloudUser u2 on a.author=u2 "
            + "where "
            + "   q.subject like %:kw% "
            + "   or q.content like %:kw% "
            + "   or u1.username like %:kw% "
            + "   or a.content like %:kw% "
            + "   or u2.username like %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);

    @Query("select distinct q " +
            "from Question q " +
            "left outer join CloudUser u1 on q.author = u1 " +
            "left outer join Answer a on a.question = q " +
            "left outer join CloudUser u2 on a.author = u2 " +
            "left outer join q.voter v " +  // Include the join with the voters
            "where " +
            "   q.subject like %:kw% " +
            "   or q.content like %:kw% " +
            "   or u1.username like %:kw% " +
            "   or a.content like %:kw% " +
            "   or u2.username like %:kw% " +
            "group by q " +
            "order by COUNT(v) desc")
    Page<Question> findAllByVoter(@Param("kw") String kw, Pageable pageable);

    @Query("select distinct q " +
            "from Question q " +
            "left outer join CloudUser u1 on q.author = u1 " +
            "left outer join Answer a on a.question = q " +
            "left outer join CloudUser u2 on a.author = u2 " +
            "left outer join q.answerList ans " +
            "where " +
            "   q.subject like %:kw% " +
            "   or q.content like %:kw% " +
            "   or u1.username like %:kw% " +
            "   or a.content like %:kw% " +
            "   or u2.username like %:kw% " +
            "group by q " +
            "order by COUNT(ans) desc")
    Page<Question> findAllByAnswer(@Param("kw") String kw, Pageable pageable);
}
