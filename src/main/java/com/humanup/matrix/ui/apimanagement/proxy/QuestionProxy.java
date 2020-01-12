package com.humanup.matrix.ui.apimanagement.proxy;

import com.humanup.matrix.ui.apimanagement.dto.PersonDTO;
import com.humanup.matrix.ui.apimanagement.dto.QuestionDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "qcm-app-v1")
public interface QuestionProxy {

    @CachePut(cacheNames = "question_by_questionId", key = "#question.questionId")
    @PostMapping(value="/question")
    String saveQuestion(@RequestBody QuestionDTO question);

    @Cacheable(cacheNames ="question-all")
    @GetMapping(value="/question/all")
    String findAllQuestion();

}
