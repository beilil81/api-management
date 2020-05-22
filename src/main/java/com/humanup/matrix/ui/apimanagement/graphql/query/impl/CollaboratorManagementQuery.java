package com.humanup.matrix.ui.apimanagement.graphql.query.impl;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.humanup.matrix.ui.apimanagement.dto.*;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryCollaboratormanagement;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryPerson;
import com.humanup.matrix.ui.apimanagement.proxy.*;
import com.humanup.matrix.ui.apimanagement.vo.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CollaboratorManagementQuery implements GraphQLQueryResolver, IQueryCollaboratormanagement {
  private static final Logger LOGGER = LoggerFactory.getLogger(CollaboratorManagementQuery.class);

  @Autowired CollaboratorManagementProxy collaboratorManagementProxy;


  @Override
  public List<ProjectVO> getListProject() {
    List<ProjectDTO> projectDTOS  = null;
    try {
      projectDTOS =
              ObjectBuilder.mapper.readValue(
                      collaboratorManagementProxy.findallProject(), new TypeReference<List<ProjectDTO>>() {});
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<Project> ", e);
    }
    return getProjectVOS(projectDTOS);
  }

  @Override
  public List<ProjectVO> getListProjectByEmail(@NotNull String email) {
    List<ProjectDTO> projectDTOS  = null;
    try {
      projectDTOS =
              ObjectBuilder.mapper.readValue(
                      collaboratorManagementProxy.findProjectsCollaboratorByEmail(email), new TypeReference<List<ProjectDTO>>() {});
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<Project> ", e);
    }
    return getProjectVOS(projectDTOS);
  }

  @Override
  public List<InterviewVO> getListInterview() {
    List<InterviewDTO> interviewDTOS  = null;
    try {
      interviewDTOS =
              ObjectBuilder.mapper.readValue(
                      collaboratorManagementProxy.findInteviews(), new TypeReference<List<InterviewDTO>>() {});
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<Project> ", e);
    }
    return getInterviewVOS(interviewDTOS);
  }

  @Override
  public List<InterviewVO> getListInterviewByEmail(@NotNull String email) {
    List<InterviewDTO> interviewDTOS  = null;
    try {
      interviewDTOS =
              ObjectBuilder.mapper.readValue(
                      collaboratorManagementProxy.findInteviewsByCollaboratuerEmail(email), new TypeReference<List<InterviewDTO>>() {});
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<Project> ", e);
    }
    return getInterviewVOS(interviewDTOS);
  }

  @NotNull
  private List<ProjectVO> getProjectVOS(List<ProjectDTO> projectDTOS) {
    return projectDTOS.stream()
            .map(
                    project -> {
                      return ProjectVO.builder()
                              .projectDescription(project.getProjectDescription())
                              .projectTitle(project.getProjectTitle())
                              .build();
                    })
            .collect(Collectors.toList());
  }

  @NotNull
  private List<InterviewVO> getInterviewVOS(List<InterviewDTO> interviewDTOS) {
    return interviewDTOS.stream()
            .map(
                    interview -> {
                      return InterviewVO.builder()
                              .interviewDate(interview.getInterviewDate())
                              .interviewTitle(interview.getInterviewTitle())
                              .interviewDescription(interview.getInterviewDescription())
                              .build();
                    })
            .collect(Collectors.toList());
  }
}
