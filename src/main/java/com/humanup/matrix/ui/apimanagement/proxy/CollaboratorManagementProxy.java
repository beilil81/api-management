package com.humanup.matrix.ui.apimanagement.proxy;

import com.humanup.matrix.ui.apimanagement.dto.CollaboratorDTO;
import com.humanup.matrix.ui.apimanagement.dto.PersonDTO;
import com.humanup.matrix.ui.apimanagement.dto.ProjectDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "collaborator-management-app-v1")
public interface CollaboratorManagementProxy {
    @CachePut(cacheNames = "collaborator-by-email", key = "#collaborator.mailAdresses")
    @PostMapping(value="/collaborator")
    String saveCollaborator(@RequestBody CollaboratorDTO collaborator);

    @CachePut(cacheNames = "project-by-title", key = "#project.projectTitle")
    @PostMapping(value="/project")
    String saveProject(@RequestBody ProjectDTO project);

    @Cacheable(cacheNames ="collaborator-all")
    @GetMapping(value="/collaborator/all")
    String findCollaborators();

    @Cacheable(cacheNames ="project-all")
    @GetMapping(value="/project/all")
    String findallProject();


    @Cacheable(cacheNames ="interview-all")
    @GetMapping(value="/interview/all")
    String findInteviews();

    @Cacheable(cacheNames ="interview-collaborator-all-by-email", key = "#email")
    @GetMapping(value="/collaborator/all/interview")
    String findInteviewsByCollaboratuerEmail(@RequestParam(value = "email", defaultValue = "robot@sqli.com") String email);

    @Cacheable(cacheNames = "projects-collaborator-by-email", key = "#mailAdresse")
    @RequestMapping(value="/project/collaborator/all", method= RequestMethod.GET)
    String findProjectsCollaboratorByEmail(@RequestParam(value = "mailAdresse", defaultValue = "robot@sqli.com") String mailAdresse);
}
