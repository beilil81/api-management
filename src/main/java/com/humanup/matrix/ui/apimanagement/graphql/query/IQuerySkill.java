package com.humanup.matrix.ui.apimanagement.graphql.query;

import com.humanup.matrix.ui.apimanagement.vo.SkillVO;
import com.humanup.matrix.ui.apimanagement.vo.TypeSkillsVO;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IQuerySkill {
  List<SkillVO> getListSkill();
}
