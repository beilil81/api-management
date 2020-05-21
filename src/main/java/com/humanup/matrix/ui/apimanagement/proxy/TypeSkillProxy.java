package com.humanup.matrix.ui.apimanagement.proxy;

import com.humanup.matrix.ui.apimanagement.dto.ProfileDTO;
import com.humanup.matrix.ui.apimanagement.dto.TypeSkillsDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "collaborator-app-v1")
public interface TypeSkillProxy {

    @CachePut(cacheNames = "typeskill-by-title", key = "#skill.titleSkill")
    @PostMapping(value="/typeskills")
    String saveTypeSkill(@RequestBody TypeSkillsDTO skill);

    @Cacheable(cacheNames ="typeskill-all")
    @GetMapping(value="/typeskills/all")
    String findAllTypeSkills();

    @Cacheable(cacheNames ="typeskill-by-title",key = "#title")
    @GetMapping(value="/typeskills/title")
    String findTypeSkillByTitle(@RequestParam(value = "title", defaultValue = "Spring") String title);

    @Cacheable(cacheNames ="typeskill-by-id",key = "#id")
    @GetMapping(value="/typeskills/id")
    String findTypeSkillByID(@RequestParam(value = "id", defaultValue = "1") Long id);
}
