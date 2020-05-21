package com.humanup.matrix.ui.apimanagement.graphql.query;

import com.humanup.matrix.ui.apimanagement.vo.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IQueryTrainer {
  List<TrainerVO> getListTrainers();
  TrainerVO getTrainersByEmail(@NotNull final String email);
}
