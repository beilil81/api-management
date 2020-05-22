package com.humanup.matrix.ui.apimanagement.graphql.mutation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.vo.PersonVO;
import com.humanup.matrix.ui.apimanagement.vo.SkillVO;
import com.humanup.matrix.ui.apimanagement.vo.TypeSkillsVO;
import org.jetbrains.annotations.NotNull;

public interface ISkillMutation {

    SkillVO createSkill(@NotNull   Long idTypeSkills, @NotNull  String libelle, @NotNull  String description) throws JsonProcessingException ;

    }
