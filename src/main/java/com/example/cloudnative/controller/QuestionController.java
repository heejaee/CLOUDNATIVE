package com.example.cloudnative.controller;

import com.example.cloudnative.controller.form.AnswerForm;
import com.example.cloudnative.controller.form.QuestionForm;
import com.example.cloudnative.domain.CloudUser;
import com.example.cloudnative.domain.Question;
import com.example.cloudnative.service.QuestionService;
import com.example.cloudnative.service.UserService;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw,
                       @RequestParam(value = "sort", defaultValue = "recent") String sort) {
        // 페이징과 정렬을 하여 질문들을 보여줍니다.
        Page<Question> paging = questionService.findBySort(page, kw, sort);

        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        model.addAttribute("sort", sort);
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id, AnswerForm answerForm) {
        Question question = questionService.findQuestion(id);
        // 질문상세페이지에 들어오면 조회수가 증가합니다.
        questionService.plusView(question);

        model.addAttribute("question", question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        CloudUser user = userService.findUser(principal.getName());
        questionService.create(questionForm.getSubject(), questionForm.getContent(), user);
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable Integer id, Principal principal) {
        Question question = questionService.findQuestion(id);
        // 작성자가 아니면 글을 수정할 수 없습니다.
        if(!question.isAuthorNameEquals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        questionForm.modify(question.getSubject(), question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        Question question = questionService.findQuestion(id);
        if (!question.isAuthorNameEquals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable Integer id) {
        Question question = questionService.findQuestion(id);
        if (!question.isAuthorNameEquals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        questionService.delete(question);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable Integer id) {
        Question question = questionService.findQuestion(id);
        CloudUser user = userService.findUser(principal.getName());

        questionService.vote(question, user);
        return String.format("redirect:/question/detail/%s", id);
    }
}
