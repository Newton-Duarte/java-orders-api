package com.newtonduarte.orders_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newtonduarte.orders_api.TestDataUtils;
import com.newtonduarte.orders_api.domain.dto.CreateUserDto;
import com.newtonduarte.orders_api.domain.dto.SignInDto;
import com.newtonduarte.orders_api.domain.entities.UserEntity;
import com.newtonduarte.orders_api.services.AuthService;
import com.newtonduarte.orders_api.services.ProductService;
import com.newtonduarte.orders_api.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthControllerIntegrationTests {
    private final MockMvc mockMvc;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthControllerIntegrationTests(MockMvc mockMvc, UserService userService) {
        this.mockMvc = mockMvc;
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatUserCanSignInWithCorrectUsernameAndPassword() throws Exception {
        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        String mockUserPassword = "123456";
        testCreateUserDtoA.setPassword(mockUserPassword);
        UserEntity savedUserEntity = userService.createUser(testCreateUserDtoA);
        SignInDto signInDto = TestDataUtils.createSignInDto(savedUserEntity.getEmail(), mockUserPassword);
        String signInJson = objectMapper.writeValueAsString(signInDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signInJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatUserCanSignInAndReceiveJwtToken() throws Exception {
        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        String mockUserPassword = "123456";
        testCreateUserDtoA.setPassword(mockUserPassword);
        UserEntity savedUserEntity = userService.createUser(testCreateUserDtoA);
        SignInDto signInDto = TestDataUtils.createSignInDto(savedUserEntity.getEmail(), mockUserPassword);
        String signInJson = objectMapper.writeValueAsString(signInDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signInJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.token").isString()
        );
    }

    @Test
    public void testThatUserCannotSignInWithIncorrectUsernameAndPassword() throws Exception {
        SignInDto signInDto = TestDataUtils.createSignInDto("incorrect@email.com", "incorrect-password");
        String signInJson = objectMapper.writeValueAsString(signInDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signInJson)
        ).andExpect(
                MockMvcResultMatchers.status().isUnauthorized()
        );
    }

    @Test
    public void testThatUserCannotSignInWithCorrectUsernameAndIncorrectPassword() throws Exception {
        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        String mockUserPassword = "123456";
        testCreateUserDtoA.setPassword(mockUserPassword);
        UserEntity savedUserEntity = userService.createUser(testCreateUserDtoA);
        SignInDto signInDto = TestDataUtils.createSignInDto(savedUserEntity.getEmail(), "incorrect-password");
        String signInJson = objectMapper.writeValueAsString(signInDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signInJson)
        ).andExpect(
                MockMvcResultMatchers.status().isUnauthorized()
        );
    }
}
