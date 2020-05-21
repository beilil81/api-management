package com.humanup.matrix.ui.apimanagement.graphql.query;

import com.humanup.matrix.ui.apimanagement.vo.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IQueryReview {
  List<ReviewVO> getListReviews();
  List<ReviewVO> getListReviewsByEmail(@NotNull final String email);
}
