package com.humanup.matrix.ui.apimanagement.graphql.mutation.impl;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.dto.*;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.mutation.ITrainingMutation;
import com.humanup.matrix.ui.apimanagement.proxy.*;
import com.humanup.matrix.ui.apimanagement.vo.TrainerVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TrainingMutation implements GraphQLMutationResolver, ITrainingMutation {


  @Autowired TrainerProxy trainerProxy;

  @Override
  public TrainerVO createTrainer(
      @NotNull String name, @NotNull String address, @NotNull String phone, @NotNull String email)
      throws JsonProcessingException {
    final TrainerDTO trainerDto =
        TrainerDTO.builder().name(name).address(address).phone(phone).email(email).build();
    final TrainerDTO saveTrainer =
        ObjectBuilder.mapper.readValue(trainerProxy.saveTrainer(trainerDto), TrainerDTO.class);
    return Optional.ofNullable(saveTrainer)
        .map(
            trainer ->
                TrainerVO.builder()
                    .name(trainer.getName())
                    .address(trainer.getAddress())
                    .phone(trainer.getPhone())
                    .email(trainer.getEmail())
                    .build())
        .orElse(null);
  }

}
