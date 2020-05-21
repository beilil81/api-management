package com.humanup.matrix.ui.apimanagement.graphql.mutation.impl;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.mutation.IPersonMutation;
import com.humanup.matrix.ui.apimanagement.proxy.PersonProxy;
import com.humanup.matrix.ui.apimanagement.dto.PersonDTO;
import com.humanup.matrix.ui.apimanagement.vo.PersonVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonMutation implements GraphQLMutationResolver , IPersonMutation {
    @Autowired
    PersonProxy personProxy;


    @Override
    public PersonVO createPerson(@NotNull String firstName, @NotNull String lastName, @NotNull String mailAdresses, String birthDate, @NotNull String profile) throws JsonProcessingException{
        PersonDTO person = PersonDTO.builder()
                .birthDate(birthDate)
                .firstName(firstName)
                .lastName(lastName)
                .mailAdresses(mailAdresses)
                .profile(profile)
                .build();
        PersonDTO savePerson = ObjectBuilder.mapper.readValue(personProxy.savePerson(person),PersonDTO.class);
        return  PersonVO.builder()
                .birthDate(savePerson.getBirthDate())
                .firstName(savePerson.getFirstName())
                .lastName(savePerson.getLastName())
                .mailAdresses(savePerson.getMailAdresses())
        .build();
    }
}
