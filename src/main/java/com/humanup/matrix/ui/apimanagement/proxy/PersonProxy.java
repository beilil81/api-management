package com.humanup.matrix.ui.apimanagement.proxy;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.humanup.matrix.ui.apimanagement.dto.PersonDTO;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.cache.annotation.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "zuul-server/api-matrix")
public interface PersonProxy {
    @CachePut(cacheNames = "person-by-email", key = "#person.mailAdresses")
    @PostMapping(value="/person")
    String savePerson(@RequestBody PersonDTO person);

    @Cacheable(cacheNames ="person-all")
    @GetMapping(value="/person/all")
    String findAllPerson(@RequestHeader("Authorization") String authHeader);

    @Cacheable(cacheNames = "person-by-email", key = "#email")
    @RequestMapping(value="/person", method= RequestMethod.GET)
    String findPersonByEmail(@RequestHeader("Authorization") String authHeader,@RequestParam(value="email", defaultValue="robot@sqli.com") String email);
}
