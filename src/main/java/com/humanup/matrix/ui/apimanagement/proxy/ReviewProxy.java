package com.humanup.matrix.ui.apimanagement.proxy;

import com.humanup.matrix.ui.apimanagement.dto.ReviewDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "training-app-v1", decode404 = true)
public interface ReviewProxy {
  @CachePut(cacheNames = "review")
  @PostMapping(value = "/review")
  String saveReview(@RequestBody ReviewDTO review);

  @Cacheable(cacheNames = "reviews-by-email", key = "#email")
  @RequestMapping(value = "/review/all/internemail", method = RequestMethod.GET)
  String findReviewsByEmail(@RequestParam(value = "internEmail") String email);

  @Cacheable(cacheNames = "review-all")
  @GetMapping(value = "/review/all")
  String findAllReviews();
}
