package com.humanup.matrix.ui.apimanagement.proxy;

import com.humanup.matrix.ui.apimanagement.dto.AnswerDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "zuul-server/api-qcmmatrix")
public interface AnswerProxy {

    @CachePut(cacheNames = "answer_by_answerId", key = "#answer.answerId")
    @PostMapping(value="/answer")
    String saveAnswer(@RequestBody AnswerDTO answer);

    @Cacheable(cacheNames ="answer-all")
    @GetMapping(value="/answer/all")
    String findAllAnswer(@RequestHeader("Authorization") String authHeader);

    @Cacheable(cacheNames ="answer-all")
    @GetMapping(value="/answer/all/email")
    String findListAnswerByEmail(@RequestHeader("Authorization") String authHeader,@RequestParam(value="email", defaultValue="robot@sqli.com") String email);


    @Cacheable(cacheNames = "answer-by-id", key = "#answerId")
    @RequestMapping(value="/answer/all/choiceId", method= RequestMethod.GET)
    String findAnswerByChoiceId(@RequestHeader("Authorization") String authHeader, @RequestParam(value="choiceId", defaultValue="2") int choiceId);
}
