package com.humanup.matrix.ui.apimanagement.graphql.mutation.impl;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.dto.SkillDTO;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.mutation.ISkillMutation;
import com.humanup.matrix.ui.apimanagement.proxy.SkillProxy;
import com.humanup.matrix.ui.apimanagement.vo.SkillVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SkillMutation implements GraphQLMutationResolver , ISkillMutation {
    @Autowired
    SkillProxy skillProxy;

    @Override
    public SkillVO createSkill(@NotNull Long idTypeSkills, @NotNull String libelle, @NotNull String description) throws JsonProcessingException {
        SkillDTO skillDTO = SkillDTO.builder()
                .description(description)
                .libelle(libelle)
                .idTypeSkills(idTypeSkills)
                .build();
        SkillDTO saveSkill = ObjectBuilder.mapper.readValue(skillProxy.saveSkill(skillDTO),SkillDTO.class );
return SkillVO.builder()
        .libelle(saveSkill.getLibelle())
        .description(saveSkill.getDescription())
        .build();
    }
}
