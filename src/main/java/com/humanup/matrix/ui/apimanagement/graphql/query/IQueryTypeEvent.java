package com.humanup.matrix.ui.apimanagement.graphql.query;

import com.humanup.matrix.ui.apimanagement.vo.CourseTypeVO;
import com.humanup.matrix.ui.apimanagement.vo.TypeSkillsVO;

import java.util.List;

public interface IQueryTypeSkill {
  List<TypeSkillsVO> getListTypeSkills();
}
