package com.humanup.matrix.ui.apimanagement.graphql.query.impl;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.humanup.matrix.ui.apimanagement.dto.SkillDTO;
import com.humanup.matrix.ui.apimanagement.dto.TypeSkillsDTO;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQuerySkill;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryTypeSkill;
import com.humanup.matrix.ui.apimanagement.proxy.SkillProxy;
import com.humanup.matrix.ui.apimanagement.proxy.TypeSkillProxy;
import com.humanup.matrix.ui.apimanagement.vo.SkillVO;
import com.humanup.matrix.ui.apimanagement.vo.TypeSkillsVO;
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
public class SkillsQuery implements GraphQLQueryResolver, IQuerySkill {
  private static final Logger LOGGER = LoggerFactory.getLogger(SkillsQuery.class);

  @Autowired
  SkillProxy skillProxy;


  @Override
  public List<SkillVO> getListSkill() {
    List<SkillDTO> skillDTOS = null;
    try {
      skillDTOS =
          ObjectBuilder.mapper.readValue(
                  skillProxy.findAllSkills(), new TypeReference<List<SkillDTO>>() {});
    } catch (final JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<TypeSkillsDTO> ", e);
    }
    return Optional.ofNullable(skillDTOS).orElse(Collections.emptyList()).stream()
        .map(skill -> SkillVO.builder()
                .libelle(skill.getLibelle())
                .description(skill.getDescription())
                .typeSkills(
                        TypeSkillsVO.builder()
                                .titleSkill(null != skill.getTypeSkills() ? skill.getTypeSkills() : "")
                                .build()).build())
        .collect(Collectors.toList());
  }

}
