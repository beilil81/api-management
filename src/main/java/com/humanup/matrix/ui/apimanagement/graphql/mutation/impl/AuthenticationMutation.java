package com.humanup.matrix.ui.apimanagement.graphql.mutation.impl;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.dto.LoginDTO;
import com.humanup.matrix.ui.apimanagement.graphql.mutation.IAuthenticationMutation;
import com.humanup.matrix.ui.apimanagement.proxy.AuthenticationProxy;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationMutation implements GraphQLMutationResolver, IAuthenticationMutation {

  @Autowired
  AuthenticationProxy authenticationProxy;

  @Override
  public String authentication(
      @NotNull String email,
      @NotNull String password)
      throws JsonProcessingException {
    LoginDTO loginDTO = LoginDTO.builder().email(email).password(password).build();
    return authenticationProxy.authentication(loginDTO);
  }
}
