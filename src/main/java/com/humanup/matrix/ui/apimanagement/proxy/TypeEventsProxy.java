package com.humanup.matrix.ui.apimanagement.proxy;

import com.humanup.matrix.ui.apimanagement.dto.AnswerDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "event-matrix")
public interface TypeEventsProxy {
    @Cacheable(cacheNames = "typeEvent-by-id", key = "#typeId")
    @RequestMapping(value="/typesevents/id", method= RequestMethod.GET)
    String findByTypeEventsByID(@RequestParam(value = "typeId", defaultValue = "Spring Developer") Long typeId);
}
