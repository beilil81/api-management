package com.humanup.matrix.ui.apimanagement.proxy;

import com.humanup.matrix.ui.apimanagement.dto.SkillDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "collaborator-app-v1")
public interface SkillProxy {

    @CachePut(cacheNames = "skill", key = "#skillDTO.libelle")
    @PostMapping(value="/skill")
    String saveSkill(@RequestBody SkillDTO skillDTO);

    @Cacheable(cacheNames ="skill-by-libelle",key = "#libelle")
    @GetMapping(value="/skill")
    String findSkillByLibelle(@RequestParam(value = "libelle", defaultValue = "Java") String libelle);

    @Cacheable(cacheNames ="skill-all")
    @GetMapping(value="/skill/all")
    String findAllSkills();

    @Cacheable(cacheNames ="skill-by-type",key = "#type")
    @GetMapping(value="/skill/all/type")
    String findSkillByType(@RequestParam(value = "type", defaultValue = "Spring") String type);
}
