package com.humanup.matrix.ui.apimanagement.proxy;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "training-app-v1", decode404 = true)
public interface TrainingProxy {
    @Cacheable(cacheNames = "trainer-by-email", key = "#email")
    @RequestMapping(value="/trainer/email", method= RequestMethod.GET)
    String findTrainerByEmail(@RequestParam(value = "email", defaultValue = "robot@sqli.com") String email);
}
