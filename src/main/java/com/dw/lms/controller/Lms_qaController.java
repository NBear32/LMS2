package com.dw.lms.controller;

import com.dw.lms.dto.AnswerDto;
import com.dw.lms.dto.StatusUpdateDto;
import com.dw.lms.model.Lms_qa;
import com.dw.lms.service.Lms_qaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/qa")
public class Lms_qaController {
    @Autowired
    private Lms_qaService lms_qaService;

//    @GetMapping("/getAllItems")
//    public Page<Lms_qa> getAllQuestions(@RequestParam int page, @RequestParam int size) {
//        return lms_qaService.getQuestions(page, size);
//    }
    @GetMapping("/getAllItems")
    public Page<Lms_qa> getAllQuestions(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String category) {
        if (category != null && !category.equals("all")) {
            return lms_qaService.getQuestionsByCategory(page, size, category);
        } else {
            return lms_qaService.getQuestions(page, size);
        }
    }

    @GetMapping("/{id}")
    public Optional<Lms_qa> getQuestionById(@PathVariable Long id) {
        return lms_qaService.getQuestionById(id);
    }

    @PostMapping("/newQuestion")
    public ResponseEntity<Lms_qa> createQuestion(@RequestBody Lms_qa lms_qa) {
        // 사용자 객체가 제대로 매핑되었는지 확인합니다.
        if (lms_qa.getUser() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Lms_qa savedQuestion = lms_qaService.saveQuestion(lms_qa);
        return ResponseEntity.ok(savedQuestion);
    }

    @PutMapping("/{id}")
    public Lms_qa updateQuestion(@PathVariable Long id, @RequestBody Lms_qa lms_qa) {
        lms_qa.setLmsQaSeq(id);
        return lms_qaService.saveQuestion(lms_qa);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        lms_qaService.deleteQuestion(id);
    }


    @PostMapping("/{id}/answer")
    public Lms_qa answerQuestion(@PathVariable Long id, @RequestBody AnswerDto answerDto) {
        return lms_qaService.answerQuestion(id, answerDto);
    }

    @PutMapping("/{id}/updateStatus")
    public Lms_qa updateQuestionStatus(@PathVariable Long id, @RequestBody StatusUpdateDto statusUpdateDto) {
        return lms_qaService.updateQuestionStatus(id, statusUpdateDto);
    }

    @GetMapping("/search")
    public Page<Lms_qa> searchQuestions(@RequestParam String keyword, @RequestParam int page, @RequestParam int size) {
        return lms_qaService.searchQuestions(keyword, page, size);
    }


}
