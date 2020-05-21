package com.humanup.matrix.ui.apimanagement.graphql.query.impl;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.humanup.matrix.ui.apimanagement.dto.*;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryPerson;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryProfile;
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
public class ProfileQuery implements GraphQLQueryResolver, IQueryProfile {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProfileQuery.class);


  @Autowired ProfileProxy profileProxy;


  @Override
  public List<ProfileVO> getListProfile() {
    List<ProfileDTO> profileListDTO = null;
    try {
      profileListDTO =
              ObjectBuilder.mapper.readValue(
                      profileProxy.findAllProfile(), new TypeReference<List<ProfileDTO>>() {});

    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<ProfileDTO> ", e);
    }
    return profileListDTO.stream()
            .map(
                    p -> {
                      return ProfileVO.builder()
                              .profileTitle(p.getProfileTitle())
                              .profileDescription(p.getProfileDescription())
                              .build();
                    })
            .collect(Collectors.toList());
  }
}
