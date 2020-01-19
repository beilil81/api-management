package com.humanup.matrix.ui.apimanagement.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.humanup.matrix.ui.apimanagement.dto.*;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.interfaces.IQuery;
import com.humanup.matrix.ui.apimanagement.proxy.*;
import com.humanup.matrix.ui.apimanagement.vo.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Query implements GraphQLQueryResolver, IQuery {
    private static final Logger LOGGER = LoggerFactory.getLogger(Query.class);


    @Autowired
    PersonProxy personProxy;

    @Autowired
    ProfileProxy profileProxy;

    @Autowired
    QuestionProxy questionProxy;

    @Autowired
    ChoiceProxy choiceProxy;

    @Autowired
    AnswerProxy answerProxy;

    @Autowired
    CollaboratorManagementProxy collaboratorManagementProxy;

    @Override
    public List<PersonVO> getListPerson(){
        List<PersonDTO> personListDTO = null;
        try {
            personListDTO = ObjectBuilder.mapper.readValue(personProxy.findAllPerson(), new TypeReference<List<PersonDTO>>() {
            });

        } catch (JsonProcessingException e) {
            LOGGER.error("Exception Parsing List<PersonVO> ", e);
        }
        return personListDTO.stream()
                .map(p -> {
                    ProfileDTO profile = null;
                    try {
                        profile =  ObjectBuilder.mapper.readValue(profileProxy.findProfileByTitle(p.getProfile()), ProfileDTO.class);
                    } catch (JsonProcessingException e) {
                        LOGGER.error("Exception Parsing Profile {}", p.getProfile(), e);
                    }
                    return new PersonVO.Builder()
                            .setBirthDate(p.getBirthDate())
                            .setFirstName(p.getFirstName())
                            .setLastName(p.getLastName())
                            .setMailAdresses(p.getMailAdresses())
                            .setProfile(ObjectBuilder.buildProfile(profile))
                            .setSkills(ObjectBuilder.buildCollectionSkills(p))
                            .setInterviews(ObjectBuilder.buildCollectionInterviewByEmailPerson(p.getMailAdresses(),collaboratorManagementProxy))
                            .setProjects(ObjectBuilder.buildCollectionProjectByEmailPerson(p.getMailAdresses(),collaboratorManagementProxy))
                            .build();
                }).collect(Collectors.toList());
    }


    @Override
    public PersonVO getPersonByEmail(@NotNull final String email) {
        PersonDTO personDTO = null;
        ProfileDTO profileDTO = null;
        List<ProjectDTO> projectsDTO = null;
        try {
            personDTO =  ObjectBuilder.mapper.readValue(personProxy.findPersonByEmail(email), PersonDTO.class);
            profileDTO =  ObjectBuilder.mapper.readValue(profileProxy.findProfileByTitle(personDTO.getProfile()), ProfileDTO.class);
            projectsDTO = ObjectBuilder.mapper.readValue(collaboratorManagementProxy.findProjectsCollaboratorByEmail(email), new TypeReference<List<ProjectDTO>>() {
            });
        } catch (JsonProcessingException e) {
            LOGGER.error("Exception Parsing  Person {}", email, e);
        }
        return new PersonVO.Builder()
                .setBirthDate(personDTO.getBirthDate())
                .setFirstName(personDTO.getFirstName())
                .setLastName(personDTO.getLastName())
                .setMailAdresses(personDTO.getMailAdresses())
                .setProfile(ObjectBuilder.buildProfile(profileDTO))
                .setSkills(ObjectBuilder.buildCollectionSkills(personDTO))
                .setProjects(ObjectBuilder.buildCollectionProjects(projectsDTO))
                .build();
    }


    @Override
    public List<QuestionVO> getListQuestion() {
        List<QuestionDTO> questionListDTO = null;

        try {
            questionListDTO = ObjectBuilder.mapper.readValue(questionProxy.findAllQuestion(), new TypeReference<List<QuestionDTO>>() {
            });

        } catch (JsonProcessingException e) {
            LOGGER.error("Exception Parsing List<QuestionVO> ", e);
        }

        return questionListDTO.stream().map(q -> {
            return  QuestionVO.builder()
                    .questionId(q.getQuestionId())
                    .questionText(q.getQuestionText())
                    .build();
        })
                .collect(Collectors.toList());
    }

    @Override
    public List<ChoiceVO> getListChoice() {
        List<ChoiceDTO> choiceListDTO = null;
        try {
            choiceListDTO = ObjectBuilder.mapper.readValue(choiceProxy.findAllChoice(),new TypeReference<List<ChoiceDTO>>() {});
        }catch (JsonProcessingException e) {
            LOGGER.error("Exception Parsing List<ChoiceVO> ", e);
        }
        return choiceListDTO.stream().map(c -> {
            QuestionDTO questionDTO = null;
            try{
                questionDTO = ObjectBuilder.mapper.readValue(questionProxy.findQuestionByQuestionId(c.getQuestionId()), QuestionDTO.class);
            }catch (JsonProcessingException e){
                LOGGER.error("Exception Parsing Question {}", c.getQuestionId(), e);
            }
            return ChoiceVO.builder()
                    .choiceText(c.getChoiceText())
                    .percentage(c.getPercentage())
                    .question(ObjectBuilder.buildQuestion(questionDTO))
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<ChoiceVO> getChoicesByQuestionId(@NotNull final Long questionId) {

        List<ChoiceDTO> choiceListDTO = null;
        try {
            choiceListDTO = ObjectBuilder.mapper.readValue(choiceProxy.findChoicesByQuestionId(questionId),new TypeReference<List<ChoiceDTO>>() {});
        }catch (JsonProcessingException e) {
            LOGGER.error("Exception Parsing List<ChoiceVO> ", e);
        }
        return choiceListDTO.stream().map(c -> {
            QuestionDTO questionDTO = null;
            try{
                questionDTO = ObjectBuilder.mapper.readValue(questionProxy.findQuestionByQuestionId(c.getQuestionId()), QuestionDTO.class);
            }catch (JsonProcessingException e){
                LOGGER.error("Exception Parsing Question {}", c.getQuestionId(), e);
            }
            return ChoiceVO.builder()
                    .choiceText(c.getChoiceText())
                    .percentage(c.getPercentage())
                    .question(ObjectBuilder.buildQuestion(questionDTO))
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<AnswerVO> getListAnswer() {
        List<AnswerDTO> answerListDTO = null;
        try {
            answerListDTO = ObjectBuilder.mapper.readValue(answerProxy.findAllAnswer(),new TypeReference<List<AnswerDTO>>() {});
        }catch (JsonProcessingException e) {
            LOGGER.error("Exception Parsing List<AnswerDTO> ", e);
        }
        return answerListDTO.stream().map(c -> {
            ChoiceDTO choiceDTO = null;
            PersonDTO personDTO = null;
            try{
                choiceDTO = ObjectBuilder.mapper.readValue(choiceProxy.findChoicesByChoiceId(c.getChoiceId()), ChoiceDTO.class);
                personDTO = ObjectBuilder.mapper.readValue(personProxy.findPersonByEmail(c.getEmailPerson()), PersonDTO.class);
            }catch (JsonProcessingException e){
                LOGGER.error("Exception Parsing Answer {}", e);
            }
            return AnswerVO.builder()
                    .choice(ObjectBuilder.buildChoice(choiceDTO))
                    .person(ObjectBuilder.buildPerson(personDTO))
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public AnswerVO getAnswerByChoiceId(@NotNull Long choiceId) {
        ChoiceDTO choiceDTO = null;
        PersonDTO personDTO = null;
        try {
            choiceDTO = ObjectBuilder.mapper.readValue(choiceProxy.findChoicesByQuestionId(choiceDTO.getQuestionId()), ChoiceDTO.class);
            personDTO = ObjectBuilder.mapper.readValue(personProxy.findPersonByEmail(personDTO.getMailAdresses()),PersonDTO.class);
        }catch (JsonProcessingException e) {
            LOGGER.error("Exception Parsing List<AnswerVO> ", e);
        }
        return AnswerVO.builder()
                    .choice(ObjectBuilder.buildChoice(choiceDTO))
                    .person(ObjectBuilder.buildPerson(personDTO))
                    .build();
    }

}
