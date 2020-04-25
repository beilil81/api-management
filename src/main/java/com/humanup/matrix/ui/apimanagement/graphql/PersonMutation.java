package com.humanup.matrix.ui.apimanagement.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.interfaces.IPersonMutation;
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
        PersonDTO person = new PersonDTO.Builder()
                .setBirthDate(birthDate)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setMailAdresses(mailAdresses)
                .setProfile(profile)
                .build();
        PersonDTO savePerson = ObjectBuilder.mapper.readValue(personProxy.savePerson(person),PersonDTO.class);
        return  PersonVO.builder()
        .mailAdresses(savePerson.getMailAdresses())
        .build();
    }
}
