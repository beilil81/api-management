package com.humanup.matrix.ui.apimanagement.proxy;

import com.humanup.matrix.ui.apimanagement.dto.EventDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "zuul-server/api-eventmatrix")
public interface EventProxy {

    @CachePut(cacheNames = "event", key = "#eventDTO.libelle")
    @PostMapping(value="/event")
    String saveEvent(@RequestBody EventDTO eventDTO);

    @Cacheable(cacheNames ="event-all")
    @GetMapping(value="/event/all")
    String findAllEvent(@RequestHeader("Authorization") String authHeader);

    @Cacheable(cacheNames = "events-by-email", key = "#emailPerson")
    @RequestMapping(value = "/event/all/person", method = RequestMethod.GET)
    String findEventsByEmail(@RequestParam(value="email", defaultValue="robot@sqli.com") String email, @RequestHeader("Authorization") String authHeader);

    @Cacheable(cacheNames = "event-by-type", key = "#type")
    @RequestMapping(value="/event/all/type", method= RequestMethod.GET)
    String findListEventByType(@RequestParam(value="type", defaultValue="2") Long type);
}
