package com.humanup.matrix.ui.apimanagement.proxy;

import com.humanup.matrix.ui.apimanagement.dto.EventDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "event-matrix")
public interface EventProxy {

    @CachePut(cacheNames = "event_by_id", key = "#event.id")
    @PostMapping(value="/event")
    String saveEvent(@RequestBody EventDTO event);

    @Cacheable(cacheNames ="event-all")
    @GetMapping(value="/event/all")
    String findAllEvent(@RequestHeader("Authorization") String authHeader);
    @Cacheable(cacheNames = "reviews-by-email", key = "#email")
    @RequestMapping(value = "/review/all/internemail", method = RequestMethod.GET)
    String findEventsByEmail(@RequestHeader("Authorization") String authHeader,@RequestParam(value="email", defaultValue="robot@sqli.com") String email);


    @Cacheable(cacheNames = "event-by-type", key = "#type")
    @RequestMapping(value="/event/all/type", method= RequestMethod.GET)
    String findListEventByType(@RequestParam(value="type", defaultValue="2") Long type);
}
