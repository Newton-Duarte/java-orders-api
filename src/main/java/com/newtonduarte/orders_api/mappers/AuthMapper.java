package com.newtonduarte.orders_api.mappers;

import com.newtonduarte.orders_api.domain.SignInRequest;
import com.newtonduarte.orders_api.domain.SignUpRequest;
import com.newtonduarte.orders_api.domain.dto.SignInDto;
import com.newtonduarte.orders_api.domain.dto.SignUpDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {
    SignInRequest toSignInRequest(SignInDto signInDto);
    SignUpRequest toSignUpRequest(SignUpDto signUpDto);
}
