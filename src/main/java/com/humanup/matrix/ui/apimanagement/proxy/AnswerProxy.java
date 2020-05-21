package com.humanup.matrix.ui.apimanagement.proxy;

import com.humanup.matrix.ui.apimanagement.dto.AnswerDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "qcm-app-v1")
public interface    AnswerProxy {

    @CachePut(cacheNames = "answer_by_answerId", key = "#answer.answerId")
    @PostMapping(value="/answer")
    String saveAnswer(@RequestBody AnswerDTO answer);

    @Cacheable(cacheNames ="answer-all")
    @GetMapping(value="/answer/all")
    String findAllAnswer();

    @Cacheable(cacheNames = "answer-by-id", key = "#answerId")
    @RequestMapping(value="/answer/all/question", method= RequestMethod.GET)
    String findAnswerByChoiceId(@RequestParam(value="choiceId", defaultValue="2") Long choiceId);
}
