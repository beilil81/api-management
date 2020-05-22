package com.humanup.matrix.ui.apimanagement.proxy;

import com.humanup.matrix.ui.apimanagement.dto.TrainerDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "training-app-v1", decode404 = true)
public interface TrainerProxy {
  @CachePut(cacheNames = "trainer")
  @PostMapping(value = "/trainer")
  String saveTrainer(@RequestBody TrainerDTO trainer);

  @Cacheable(cacheNames = "trainer-by-email", key = "#email")
  @RequestMapping(value = "/trainer/email", method = RequestMethod.GET)
  String findTrainerByEmail(@RequestParam(value = "email") String email);

  @Cacheable(cacheNames = "trainer-all")
  @GetMapping(value = "/trainer/all")
  String findAllTrainers();
}
