package com.humanup.matrix.ui.apimanagement.graphql.mutation.impl;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.dto.ProfileDTO;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.mutation.IProfileMutation;
import com.humanup.matrix.ui.apimanagement.proxy.ProfileProxy;
import com.humanup.matrix.ui.apimanagement.vo.ProfileVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileMutation implements GraphQLMutationResolver , IProfileMutation {
    @Autowired
    ProfileProxy profileProxy;


    @Override
    public ProfileVO createProfile(@NotNull String profileTitle, @NotNull String profileDescription) throws JsonProcessingException {
        ProfileDTO profile =  ProfileDTO.builder()
                .profileTitle(profileTitle)
                .profileDescription(profileDescription)
                .build();
        ProfileDTO saveProfile = ObjectBuilder.mapper.readValue(profileProxy.saveProfile(profile),ProfileDTO.class);
        return  ProfileVO.builder()
        .profileTitle(saveProfile.getProfileTitle())
         .profileDescription(saveProfile.getProfileDescription())
        .build();
    }

}
