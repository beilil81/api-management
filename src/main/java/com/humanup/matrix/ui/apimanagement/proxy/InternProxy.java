package com.humanup.matrix.ui.apimanagement.proxy;

import com.humanup.matrix.ui.apimanagement.dto.InternDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "training-app-v1", decode404 = true)
public interface InternProxy {
  @CachePut(cacheNames = "intern")
  @PostMapping(value = "/intern")
  String saveIntern(@RequestBody InternDTO intern);

  @Cacheable(cacheNames = "intern-by-id")
  @GetMapping(value = "/intern")
  String findInternById(@RequestParam(value = "id") String id);

  @Cacheable(cacheNames = "intern-by-email", key = "#email")
  @GetMapping(value = "/intern/email")
  String findInternByEmail(@RequestParam(value = "email") String email);
}
