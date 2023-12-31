package com.example.cloudnative.controller;

import com.example.cloudnative.controller.form.AnswerForm;
import com.example.cloudnative.domain.Answer;
import com.example.cloudnative.domain.CloudUser;
import com.example.cloudnative.domain.Question;
import com.example.cloudnative.service.AnswerService;
import com.example.cloudnative.service.QuestionService;
import com.example.cloudnative.service.UserService;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult,
                               Principal principal) {
        // 답변을 달 질문과 현재사용자를 찾습니다.
        Question question = questionService.findQuestion(id);
        CloudUser user = userService.findUser(principal.getName());
        // 오류시 현재화면 유지
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        //답변 생성
        Answer answer = answerService.create(question, answerForm.getContent(), user);
        return String.format("redirect:/question/detail/%s#answer_%s",
                answer.getQuestion().getId(), answer.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm, @PathVariable Integer id, Principal principal) {
        Answer answer = answerService.findAnswer(id);
        // 다른 사용자의 답변 수정 방지
        if (!answer.isAuthorNameEquals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        // answerForm 수정
        answerForm.modify(answer.getContent());
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }

        Answer answer = answerService.findAnswer(id);
        if (!answer.isAuthorNameEquals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        // 답변 수정
        answerService.modify(answer, answerForm.getContent());
        return String.format("redirect:/question/detail/%s#answer_%s",
                answer.getQuestion().getId(), answer.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
        Answer answer = answerService.findAnswer(id);
        if (!answer.isAuthorNameEquals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        // 답변 삭제
        answerService.delete(answer);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String answerVote(Principal principal, @PathVariable Integer id) {
        Answer answer = answerService.findAnswer(id);
        CloudUser user = userService.findUser(principal.getName());
        // 답변 추천
        answerService.vote(answer, user);
        return String.format("redirect:/question/detail/%s#answer_%s",
                answer.getQuestion().getId(), answer.getId());
    }
}
