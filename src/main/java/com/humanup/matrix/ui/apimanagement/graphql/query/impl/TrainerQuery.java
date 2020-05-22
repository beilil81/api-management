package com.humanup.matrix.ui.apimanagement.graphql.query.impl;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.humanup.matrix.ui.apimanagement.dto.*;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryPerson;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryTrainer;
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
public class TrainerQuery implements GraphQLQueryResolver, IQueryTrainer {
  private static final Logger LOGGER = LoggerFactory.getLogger(TrainerQuery.class);

  @Autowired TrainerProxy trainerProxy;

  @Override
  public List<TrainerVO> getListTrainers() {
    List<TrainerDTO> trainerListDTO = null;
    try {
      trainerListDTO =
          ObjectBuilder.mapper.readValue(
              trainerProxy.findAllTrainers(), new TypeReference<List<TrainerDTO>>() {});
    } catch (final JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<TrainerDTO> ", e);
    }
    return getTrainerVOS(trainerListDTO);
  }

  @Override
  public TrainerVO getTrainersByEmail(@NotNull String email) {
    TrainerDTO trainerDTO = null;
    try {
      trainerDTO =
              ObjectBuilder.mapper.readValue(
                      trainerProxy.findTrainerByEmail(email), TrainerDTO.class);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<TrainerDTO> ", e);
    }
    return TrainerVO.builder()
            .name(trainerDTO.getName())
            .address(trainerDTO.getAddress())
            .email(trainerDTO.getEmail())
            .phone(trainerDTO.getPhone())
            .build();
  }

  @NotNull
  private List<TrainerVO> getTrainerVOS(List<TrainerDTO> trainerListDTO) {
    return Optional.ofNullable(trainerListDTO).orElse(Collections.emptyList()).stream()
            .map(
                    trainer ->
                            TrainerVO.builder()
                                    .name(trainer.getName())
                                    .address(trainer.getAddress())
                                    .email(trainer.getEmail())
                                    .phone(trainer.getPhone())
                                    .build())
            .collect(Collectors.toList());
  }

}
