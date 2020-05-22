package com.humanup.matrix.ui.apimanagement.proxy;

import com.humanup.matrix.ui.apimanagement.dto.CourseTypeDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "training-app-v1", decode404 = true)
public interface CourseTypeProxy {
  @CachePut(cacheNames = "course-type")
  @PostMapping(value = "/coursetype")
  String saveCourseType(@RequestBody CourseTypeDTO courseType);

  @Cacheable(cacheNames = "course-type-all")
  @GetMapping(value = "/coursetype/all")
  String findAllCourseTypes();
}
