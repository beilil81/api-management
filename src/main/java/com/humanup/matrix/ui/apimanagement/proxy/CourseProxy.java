package com.humanup.matrix.ui.apimanagement.proxy;

import com.humanup.matrix.ui.apimanagement.dto.CourseDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "training-app-v1", decode404 = true)
public interface CourseProxy {
  @CachePut(cacheNames = "course")
  @PostMapping(value = "/course")
  String saveCourse(@RequestBody CourseDTO course);

  @Cacheable(cacheNames = "course-by-id")
  @GetMapping(value = "/course")
  String findCourseById(@RequestParam(value = "id") String id);

  @Cacheable(cacheNames = "courses-by-email", key = "#email")
  @RequestMapping(value = "/course/all/internemail", method = RequestMethod.GET)
  String findCoursesByEmail(@RequestParam(value = "internEmail") String email);

  @Cacheable(cacheNames = "course-all")
  @GetMapping(value = "/course/all")
  String findAllCourses();
}
