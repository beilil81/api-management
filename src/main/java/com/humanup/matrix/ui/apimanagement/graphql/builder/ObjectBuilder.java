package com.humanup.matrix.ui.apimanagement.graphql.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.humanup.matrix.ui.apimanagement.dto.*;
import com.humanup.matrix.ui.apimanagement.proxy.CollaboratorManagementProxy;
import com.humanup.matrix.ui.apimanagement.proxy.CourseProxy;
import com.humanup.matrix.ui.apimanagement.proxy.ReviewProxy;
import com.humanup.matrix.ui.apimanagement.vo.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ObjectBuilder {
  private static final Logger LOGGER = LoggerFactory.getLogger(ObjectBuilder.class);
  private static final DateTimeFormatter inputFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
  private static final DateTimeFormatter outputFormatter =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
  public static final ObjectMapper mapper = new ObjectMapper();

  static {
    final LocalDateTimeDeserializer dateTimeDeserializer =
        new LocalDateTimeDeserializer(inputFormatter);
    final LocalDateTimeSerializer dateTimeSerializer = new LocalDateTimeSerializer(inputFormatter);
    final JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addDeserializer(LocalDateTime.class, dateTimeDeserializer);
    javaTimeModule.addSerializer(LocalDateTime.class, dateTimeSerializer);
    mapper.registerModule(javaTimeModule);
  }

  @NotNull
  public static PersonVO buildPerson(@NotNull final PersonDTO person) {
    if(null!=person)
    return PersonVO.builder()
        .mailAdresses(person.getMailAdresses())
        .firstName(person.getFirstName())
        .lastName(person.getLastName())
        .birthDate(person.getBirthDate())
        .build();
    return null;
  }

  @NotNull
  public static ChoiceVO buildChoice(@NotNull final ChoiceDTO choice) {
    if(null!=choice)
    return ChoiceVO.builder()
        .choiceText(choice.getChoiceText())
        .percentage(choice.getPercentage())
        .build();
    return null;
  }

  @NotNull
  public static QuestionVO buildQuestion(@NotNull final QuestionDTO question) {
    if(null!=question)
    return QuestionVO.builder()
        .questionId(question.getQuestionId())
        .questionText(question.getQuestionText())
        .build();
    return null;
  }

  @NotNull
  public static List<CourseVO> buildCollectionCourseByEmailPerson(
      @NotNull final String email, @NotNull final CourseProxy courseProxy) {
    try {
      List<CourseDTO> coursesDTO =
          mapper.readValue(
              courseProxy.findCoursesByEmail(email), new TypeReference<List<CourseDTO>>() {});
      return coursesDTO.stream()
              .map(
                      course -> {
                        return CourseVO.builder()
                                .courseTypeTitle(course.getCourseTypeTitle())
                                .description(course.getDescription())
                                .title(course.getTitle())
                                .startDate(
                                        Optional.ofNullable(course.getStartDate())
                                                .map(date -> date.format(outputFormatter))
                                                .orElse(null))
                                .endDate(
                                        Optional.ofNullable(course.getEndDate())
                                                .map(date -> date.format(outputFormatter))
                                                .orElse(null))
                                .build();
                      })
              .collect(Collectors.toList());
    } catch (final Exception e) {
      LOGGER.error("Exception Parsing List<CourseVO> {}", email, e);
      return Collections.emptyList();
    }

  }

  @NotNull
  public static List<ReviewVO> buildCollectionReviewByEmailPerson(
      @NotNull final String email, @NotNull final ReviewProxy reviewProxy) {
    try {
      List<ReviewDTO> reviewsDTO =
          mapper.readValue(
              reviewProxy.findReviewsByEmail(email), new TypeReference<List<ReviewDTO>>() {});
      return reviewsDTO.stream()
              .map(
                      review -> {
                        return ReviewVO.builder()
                                .courseTitle(review.getCourseTitle())
                                .internEmail(review.getInternEmail())
                                .createdOn(
                                        Optional.ofNullable(review.getCreatedOn())
                                                .map(date -> date.format(outputFormatter))
                                                .orElse(null))
                                .score(review.getScore())
                                .build();
                      })
              .collect(Collectors.toList());
    } catch (final Exception e) {
      LOGGER.error("Exception Parsing List<ReviewVO> {}", email, e);
      return Collections.emptyList();
    }

  }

  @NotNull
  public static ProfileVO buildProfile(@NotNull final ProfileDTO profile) {
    if(null!=profile)
    return  ProfileVO.builder()
        .profileTitle(profile.getProfileTitle())
        .profileDescription(profile.getProfileDescription())
        .countPerson(profile.getCountPerson())
        .build();
    return null;
  }

  @NotNull
  public static List<InterviewVO> buildCollectionInterviewByEmailPerson(
      @NotNull final String email,
      @NotNull final CollaboratorManagementProxy collaboratorManagementProxy) {
    try {
      List<InterviewDTO> interviewListDTO =
          mapper.readValue(
              collaboratorManagementProxy.findInteviewsByCollaboratuerEmail(email),
              new TypeReference<List<InterviewDTO>>() {});
      return interviewListDTO.stream()
              .map(
                      interview -> {
                        return  InterviewVO.builder()
                                .interviewTitle(interview.getInterviewTitle())
                                .interviewDescription(interview.getInterviewDescription())
                                .interviewDate(interview.getInterviewDate())
                                .build();
                      })
              .collect(Collectors.toList());
    } catch (Exception e) {
      LOGGER.error("Exception Parsing   List<InterviewVO>  {}", email, e);
      return Collections.emptyList();
    }


  }

  @NotNull
  public static List<ProjectVO> buildCollectionProjectByEmailPerson(
      @NotNull final String email,
      @NotNull final CollaboratorManagementProxy collaboratorManagementProxy) {
    try {
      List<ProjectDTO> projectListDTO  =
          mapper.readValue(
              collaboratorManagementProxy.findProjectsCollaboratorByEmail(email),
              new TypeReference<List<ProjectDTO>>() {});

      return projectListDTO.stream()
              .map(
                      project -> {
                        return  ProjectVO.builder()
                                .projectTitle(project.getProjectTitle())
                                .projectDescription(project.getProjectDescription())
                                .build();
                      })
              .collect(Collectors.toList());
    } catch (Exception e) {
      LOGGER.error("Exception Parsing   List<ProjectVO>  {}", email, e);
      return Collections.emptyList();
    }

  }

  @NotNull
  public static List<ProjectVO> buildCollectionProjects(@NotNull final List<ProjectDTO> p) {
    try {
      return p.stream()
              .map(
                      project -> {
                        return  ProjectVO.builder()
                                .projectTitle(project.getProjectTitle())
                                .projectDescription(project.getProjectDescription())
                                .build();
                      })
              .collect(Collectors.toList());
    }
    catch (Exception e) {
        LOGGER.error("Exception Parsing   List<ProjectVO>  ", e);
        return Collections.emptyList();
      }
  }

  @NotNull
  public static List<SkillVO> buildCollectionSkills(@NotNull final PersonDTO p) {
    try {
      return p.getSkillVOList().stream()
              .map(
                      skill -> {
                        return  SkillVO.builder()
                                .description(skill.getDescription())
                                .libelle(skill.getLibelle())
                                .typeSkills(
                                         TypeSkillsVO.builder()
                                                .titleSkill(null != skill.getTypeSkills() ? skill.getTypeSkills() : "")
                                                .build())
                                .build();
                      })
              .collect(Collectors.toList());
    } catch (Exception e) {
      LOGGER.error("Exception Parsing   List<ProjectVO>  ", e);
      return Collections.emptyList();
    }
  }
}
