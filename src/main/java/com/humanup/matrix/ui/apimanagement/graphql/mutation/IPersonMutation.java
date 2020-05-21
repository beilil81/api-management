package com.humanup.matrix.ui.apimanagement.graphql.mutation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.vo.PersonVO;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IPersonMutation {

     PersonVO createPerson(@NotNull String firstName, @NotNull String lastName, @NotNull String mailAdresses, String birthDate, @NotNull String profile) throws JsonProcessingException ;

    }
