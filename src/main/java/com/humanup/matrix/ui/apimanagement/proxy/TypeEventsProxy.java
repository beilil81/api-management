package com.humanup.matrix.ui.apimanagement.proxy;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.humanup.matrix.ui.apimanagement.dto.TypeEventsDTO;

@FeignClient(name = "zuul-server/api-eventmatrix")
public interface TypeEventsProxy {
    @Cacheable(cacheNames = "typeEvent-by-id", key = "#typeId")
    @RequestMapping(value="/typesevents/id", method= RequestMethod.GET)
    String findByTypeEventsByID(@RequestParam(value = "typeId", defaultValue = "Spring Developer") Long typeId);
    
    
    @CachePut(cacheNames = "typeevent-by-title", key = "#event.titleEvent")
    @PostMapping(value="/typeevents")
    String saveTypeEvent(@RequestBody TypeEventsDTO event);

    @Cacheable(cacheNames ="typeevent-all")
    @GetMapping(value="/typeevents/all")
    String findAllTypeEvents();

    @Cacheable(cacheNames ="typeevent-by-title",key = "#title")
    @GetMapping(value="/typeevents/title")
    String findTypeEventByTitle(@RequestParam(value = "title", defaultValue = "Spring") String title);

    @Cacheable(cacheNames ="typeevent-by-id",key = "#id")
    @GetMapping(value="/typeevents/id")
    String findTypeEventByID(@RequestParam(value = "id", defaultValue = "1") Long id);
}
