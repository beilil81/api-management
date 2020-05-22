package com.humanup.matrix.ui.apimanagement.graphql.mutation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.vo.PersonVO;
import com.humanup.matrix.ui.apimanagement.vo.ProfileVO;
import org.jetbrains.annotations.NotNull;

public interface IProfileMutation {

     ProfileVO createProfile(@NotNull String profileTitle, @NotNull String profileDescription) throws JsonProcessingException ;
    }
