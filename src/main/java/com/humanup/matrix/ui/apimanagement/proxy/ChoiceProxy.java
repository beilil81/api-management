package com.humanup.matrix.ui.apimanagement.proxy;

import com.humanup.matrix.ui.apimanagement.dto.ChoiceDTO;
import com.humanup.matrix.ui.apimanagement.dto.QuestionDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "qcm-app-v1")
public interface ChoiceProxy {

    @CachePut(cacheNames = "choice_by_choiceId", key = "#choice.choiceId")
    @PostMapping(value="/choice")
    String saveChoice(@RequestBody ChoiceDTO choice);

    @Cacheable(cacheNames ="choice-all")
    @GetMapping(value="/choice/all")
    String findAllChoice();

    @Cacheable(cacheNames = "choice-by-question-id", key = "#choiceId")
    @RequestMapping(value="/choice/all/question", method= RequestMethod.GET)
    String findChoicesByQuestionId(@RequestParam(value="questionId", defaultValue="2") Long questionId);

    @Cacheable(cacheNames = "choice-by-choice-id", key = "#choiceId")
    @RequestMapping(value="/choice/all/choiceId", method= RequestMethod.GET)
    String findChoicesByChoiceId(@RequestParam(value="ChoiceId", defaultValue="1") Long choiceId);

}
