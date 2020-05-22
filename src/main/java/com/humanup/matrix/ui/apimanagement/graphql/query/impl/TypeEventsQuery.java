package com.humanup.matrix.ui.apimanagement.graphql.query.impl;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.humanup.matrix.ui.apimanagement.dto.CourseTypeDTO;
import com.humanup.matrix.ui.apimanagement.dto.TypeSkillsDTO;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryCourseType;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryTypeSkill;
import com.humanup.matrix.ui.apimanagement.proxy.CourseTypeProxy;
import com.humanup.matrix.ui.apimanagement.proxy.TypeSkillProxy;
import com.humanup.matrix.ui.apimanagement.vo.CourseTypeVO;
import com.humanup.matrix.ui.apimanagement.vo.TypeSkillsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TypeSkillsQuery implements GraphQLQueryResolver, IQueryTypeSkill {
  private static final Logger LOGGER = LoggerFactory.getLogger(TypeSkillsQuery.class);

  @Autowired
  TypeSkillProxy typeSkillProxy;


  @Override
  public List<TypeSkillsVO> getListTypeSkills() {
    List<TypeSkillsDTO> typeSkillsDTOS = null;
    try {
      typeSkillsDTOS =
          ObjectBuilder.mapper.readValue(
                  typeSkillProxy.findAllTypeSkills(), new TypeReference<List<TypeSkillsDTO>>() {});
    } catch (final JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<TypeSkillsDTO> ", e);
    }
    return Optional.ofNullable(typeSkillsDTOS).orElse(Collections.emptyList()).stream()
        .map(skillType -> TypeSkillsVO.builder().titleSkill(skillType.getTitleSkill()).build())
        .collect(Collectors.toList());
  }

}
