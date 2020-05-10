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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Query implements GraphQLQueryResolver, IQuery {
  private static final Logger LOGGER = LoggerFactory.getLogger(Query.class);

  @Autowired PersonProxy personProxy;

  @Autowired ProfileProxy profileProxy;

  @Autowired QuestionProxy questionProxy;

  @Autowired ChoiceProxy choiceProxy;

  @Autowired AnswerProxy answerProxy;

  @Autowired CollaboratorManagementProxy collaboratorManagementProxy;

  @Autowired CourseProxy courseProxy;

  @Autowired CourseTypeProxy courseTypeProxy;

  @Autowired ReviewProxy reviewProxy;

  @Autowired TrainerProxy trainerProxy;

  @Override
  public List<PersonVO> getListPerson() {
    List<PersonDTO> personListDTO = null;
    try {
      personListDTO =
          ObjectBuilder.mapper.readValue(
              personProxy.findAllPerson(), new TypeReference<List<PersonDTO>>() {});

    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<PersonVO> ", e);
    }
    return personListDTO.stream()
        .map(
            p -> {
              ProfileDTO profile = null;
              try {
                profile =
                    ObjectBuilder.mapper.readValue(
                        profileProxy.findProfileByTitle(p.getProfile()), ProfileDTO.class);
              } catch (JsonProcessingException e) {
                LOGGER.error("Exception Parsing Profile {}", p.getProfile(), e);
              }
              return PersonVO.builder()
                  .birthDate(p.getBirthDate())
                  .firstName(p.getFirstName())
                  .lastName(p.getLastName())
                  .mailAdresses(p.getMailAdresses())
                  .profile(ObjectBuilder.buildProfile(profile))
                  .skillVOList(ObjectBuilder.buildCollectionSkills(p))
                  .interviews(
                      ObjectBuilder.buildCollectionInterviewByEmailPerson(
                          p.getMailAdresses(), collaboratorManagementProxy))
                  .projects(
                      ObjectBuilder.buildCollectionProjectByEmailPerson(
                          p.getMailAdresses(), collaboratorManagementProxy))
                  .courses(
                      ObjectBuilder.buildCollectionCourseByEmailPerson(
                          p.getMailAdresses(), courseProxy))
                  .reviews(
                      ObjectBuilder.buildCollectionReviewByEmailPerson(
                          p.getMailAdresses(), reviewProxy))
                  .build();
            })
        .collect(Collectors.toList());
  }

  @Override
  public PersonVO getPersonByEmail(@NotNull final String email) {
    PersonDTO personDTO = null;
    ProfileDTO profileDTO = null;
    List<ProjectDTO> projectsDTO = null;
    try {
      personDTO =
          ObjectBuilder.mapper.readValue(personProxy.findPersonByEmail(email), PersonDTO.class);
      profileDTO =
          ObjectBuilder.mapper.readValue(
              profileProxy.findProfileByTitle(personDTO.getProfile()), ProfileDTO.class);
      projectsDTO =
          ObjectBuilder.mapper.readValue(
              collaboratorManagementProxy.findProjectsCollaboratorByEmail(email),
              new TypeReference<List<ProjectDTO>>() {});
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing  Person {}", email, e);
    }
    return PersonVO.builder()
        .birthDate(personDTO.getBirthDate())
        .firstName(personDTO.getFirstName())
        .lastName(personDTO.getLastName())
        .mailAdresses(personDTO.getMailAdresses())
        .profile(ObjectBuilder.buildProfile(profileDTO))
        .skillVOList(ObjectBuilder.buildCollectionSkills(personDTO))
        .projects(ObjectBuilder.buildCollectionProjects(projectsDTO))
        .build();
  }

  @Override
  public List<QuestionVO> getListQuestion() {
    List<QuestionDTO> questionListDTO = null;

    try {
      questionListDTO =
          ObjectBuilder.mapper.readValue(
              questionProxy.findAllQuestion(), new TypeReference<List<QuestionDTO>>() {});

    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<QuestionVO> ", e);
    }

    return questionListDTO.stream()
        .map(
            q -> {
              return QuestionVO.builder()
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
      choiceListDTO =
          ObjectBuilder.mapper.readValue(
              choiceProxy.findAllChoice(), new TypeReference<List<ChoiceDTO>>() {});
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<ChoiceVO> ", e);
    }
    return choiceListDTO.stream()
        .map(
            c -> {
              QuestionDTO questionDTO = null;
              try {
                questionDTO =
                    ObjectBuilder.mapper.readValue(
                        questionProxy.findQuestionByQuestionId(c.getQuestionId()),
                        QuestionDTO.class);
              } catch (JsonProcessingException e) {
                LOGGER.error("Exception Parsing Question {}", c.getQuestionId(), e);
              }
              return ChoiceVO.builder()
                  .choiceText(c.getChoiceText())
                  .percentage(c.getPercentage())
                  .question(ObjectBuilder.buildQuestion(questionDTO))
                  .build();
            })
        .collect(Collectors.toList());
  }

  @Override
  public List<ChoiceVO> getChoicesByQuestionId(@NotNull final Long questionId) {

    List<ChoiceDTO> choiceListDTO = null;
    try {
      choiceListDTO =
          ObjectBuilder.mapper.readValue(
              choiceProxy.findChoicesByQuestionId(questionId),
              new TypeReference<List<ChoiceDTO>>() {});
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<ChoiceVO> ", e);
    }
    return choiceListDTO.stream()
        .map(
            c -> {
              QuestionDTO questionDTO = null;
              try {
                questionDTO =
                    ObjectBuilder.mapper.readValue(
                        questionProxy.findQuestionByQuestionId(c.getQuestionId()),
                        QuestionDTO.class);
              } catch (JsonProcessingException e) {
                LOGGER.error("Exception Parsing Question {}", c.getQuestionId(), e);
              }
              return ChoiceVO.builder()
                  .choiceText(c.getChoiceText())
                  .percentage(c.getPercentage())
                  .question(ObjectBuilder.buildQuestion(questionDTO))
                  .build();
            })
        .collect(Collectors.toList());
  }

  @Override
  public List<AnswerVO> getListAnswer() {
    List<AnswerDTO> answerListDTO = null;
    try {
      answerListDTO =
          ObjectBuilder.mapper.readValue(
              answerProxy.findAllAnswer(), new TypeReference<List<AnswerDTO>>() {});
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<AnswerDTO> ", e);
    }
    return answerListDTO.stream()
        .map(
            c -> {
              ChoiceDTO choiceDTO = null;
              PersonDTO personDTO = null;
              try {
                choiceDTO =
                    ObjectBuilder.mapper.readValue(
                        choiceProxy.findChoicesByChoiceId(c.getChoiceId()), ChoiceDTO.class);
                personDTO =
                    ObjectBuilder.mapper.readValue(
                        personProxy.findPersonByEmail(c.getEmailPerson()), PersonDTO.class);
              } catch (JsonProcessingException e) {
                LOGGER.error("Exception Parsing Answer {}", e);
              }
              return AnswerVO.builder()
                  .choice(ObjectBuilder.buildChoice(choiceDTO))
                  .person(ObjectBuilder.buildPerson(personDTO))
                  .build();
            })
        .collect(Collectors.toList());
  }

  @Override
  public AnswerVO getAnswerByChoiceId(@NotNull Long choiceId) {
    ChoiceDTO choiceDTO = null;
    PersonDTO personDTO = null;
    try {
      choiceDTO =
          ObjectBuilder.mapper.readValue(
              choiceProxy.findChoicesByQuestionId(choiceDTO.getQuestionId()), ChoiceDTO.class);
      personDTO =
          ObjectBuilder.mapper.readValue(
              personProxy.findPersonByEmail(personDTO.getMailAdresses()), PersonDTO.class);
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<AnswerVO> ", e);
    }
    return AnswerVO.builder()
        .choice(ObjectBuilder.buildChoice(choiceDTO))
        .person(ObjectBuilder.buildPerson(personDTO))
        .build();
  }

  @Override
  public List<CourseVO> getListCourses() {
    List<CourseDTO> courseListDTO = null;
    try {
      courseListDTO =
          ObjectBuilder.mapper.readValue(
              courseProxy.findAllCourses(), new TypeReference<List<CourseDTO>>() {});
    } catch (final JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<CourseDTO> ", e);
    }
    return Optional.ofNullable(courseListDTO).orElse(Collections.emptyList()).stream()
        .map(
            course ->
                CourseVO.builder()
                    .title(course.getTitle())
                    .description(course.getDescription())
                    .trainerEmail(course.getTrainerEmail())
                    .courseTypeTitle(course.getTitle())
                    .startDate(course.getStartDate())
                    .endDate(course.getEndDate())
                    .reviews(
                        Optional.ofNullable(course.getReviewList()).orElse(Collections.emptyList())
                            .stream()
                            .map(
                                review ->
                                    ReviewVO.builder()
                                        .courseTitle(review.getCourseTitle())
                                        .internEmail(review.getInternEmail())
                                        .score(review.getScore())
                                        .createdOn(review.getCreatedOn())
                                        .build())
                            .collect(Collectors.toList()))
                    .build())
        .collect(Collectors.toList());
  }

  @Override
  public List<ReviewVO> getListReviews() {
    List<ReviewDTO> reviewListDTO = null;
    try {
      reviewListDTO =
          ObjectBuilder.mapper.readValue(
              reviewProxy.findAllReviews(), new TypeReference<List<ReviewDTO>>() {});
    } catch (final JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<ReviewDTO> ", e);
    }
    return Optional.ofNullable(reviewListDTO).orElse(Collections.emptyList()).stream()
        .map(
            review ->
                ReviewVO.builder()
                    .internEmail(review.getInternEmail())
                    .courseTitle(review.getCourseTitle())
                    .createdOn(review.getCreatedOn())
                    .score(review.getScore())
                    .build())
        .collect(Collectors.toList());
  }

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

  @Override
  public List<CourseTypeVO> getListCourseTypes() {
    List<CourseTypeDTO> courseTypeListDTO = null;
    try {
      courseTypeListDTO =
          ObjectBuilder.mapper.readValue(
              courseTypeProxy.findAllCourseTypes(), new TypeReference<List<CourseTypeDTO>>() {});
    } catch (final JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<CourseTypeDTO> ", e);
    }
    return Optional.ofNullable(courseTypeListDTO).orElse(Collections.emptyList()).stream()
        .map(courseType -> CourseTypeVO.builder().typeTitle(courseType.getTypeTitle()).build())
        .collect(Collectors.toList());
  }
}
