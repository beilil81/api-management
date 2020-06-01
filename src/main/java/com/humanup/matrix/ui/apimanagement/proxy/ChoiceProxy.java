package com.humanup.matrix.ui.apimanagement.proxy;

import com.humanup.matrix.ui.apimanagement.dto.ChoiceDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "zuul-server/api-qcmmatrix")
public interface ChoiceProxy {

    @CachePut(cacheNames = "choice_by_choiceId", key = "#choice.choiceId")
    @PostMapping(value="/choice")
    String saveChoice(@RequestBody ChoiceDTO choice);

    @Cacheable(cacheNames ="choice-all")
    @GetMapping(value="/choice/all")
    String findAllChoice(@RequestHeader("Authorization") String authHeader);

    @Cacheable(cacheNames = "choice-by-question-id", key = "#choiceId")
    @RequestMapping(value="/choice/all/questionId", method= RequestMethod.GET)
    String findChoicesByQuestionId(@RequestParam(value="questionId", defaultValue="2") int questionId, @RequestHeader("Authorization") String authHeader);

    @Cacheable(cacheNames = "choice-by-choice-id", key = "#choiceId")
    @RequestMapping(value="/choice/all/choiceId", method= RequestMethod.GET)
    String findChoicesByChoiceId(@RequestParam(value="ChoiceId", defaultValue="1") int choiceId);

    @Cacheable(cacheNames = "choice-by-email", key = "#email")
    @RequestMapping(value = "/choice/all/email", method = RequestMethod.GET)
    String findChoiceByEmail(@RequestParam(value = "email") String email, @RequestHeader("Authorization") String authHeader);
}
