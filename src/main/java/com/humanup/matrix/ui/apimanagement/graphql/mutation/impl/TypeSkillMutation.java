package com.humanup.matrix.ui.apimanagement.graphql.mutation.impl;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.dto.TypeSkillsDTO;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.mutation.ITypeSkillMutation;
import com.humanup.matrix.ui.apimanagement.proxy.TypeSkillProxy;
import com.humanup.matrix.ui.apimanagement.vo.TypeSkillsVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TypeSkillMutation implements GraphQLMutationResolver , ITypeSkillMutation {
    @Autowired
    TypeSkillProxy typeSkillProxy;

    @Override
    public TypeSkillsVO createTypeSkill(@NotNull String titleSkill) throws JsonProcessingException {
        TypeSkillsDTO typeSkillsDTO = TypeSkillsDTO.builder()
                .titleSkill(titleSkill)
                .build();
        TypeSkillsDTO saveTypeSkill = ObjectBuilder.mapper.readValue(typeSkillProxy.saveTypeSkill(typeSkillsDTO),TypeSkillsDTO.class );

        return TypeSkillsVO.builder()
                .titleSkill(saveTypeSkill.getTitleSkill())
                .build();
    }
}
