package com.humanup.matrix.ui.apimanagement.graphql.mutation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.vo.PersonVO;
import com.humanup.matrix.ui.apimanagement.vo.TypeSkillsVO;
import org.jetbrains.annotations.NotNull;

public interface ITypeSkillMutation {

     TypeSkillsVO createTypeSkill(@NotNull  String titleSkill) throws JsonProcessingException ;

    }
