package com.newtonduarte.orders_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newtonduarte.orders_api.TestDataUtils;
import com.newtonduarte.orders_api.domain.dto.CreateUserDto;
import com.newtonduarte.orders_api.domain.dto.UpdateUserProfileDto;
import com.newtonduarte.orders_api.domain.entities.UserEntity;
import com.newtonduarte.orders_api.security.ApiUserDetails;
import com.newtonduarte.orders_api.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserProfileControllerIntegrationTests {
    private final MockMvc mockMvc;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserProfileControllerIntegrationTests(MockMvc mockMvc, UserService userService) {
        this.mockMvc = mockMvc;
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatGetUserProfileReturnsHttp200OkWhenUserIsAuthenticated() throws Exception {
        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        UserEntity savedUser = userService.createUser(testCreateUserDtoA);
        ApiUserDetails apiUserDetails = new ApiUserDetails(savedUser);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/user-profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(apiUserDetails))
                        .requestAttr("userId", 1L)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetUserProfileReturnsUserWhenUserIsAuthenticated() throws Exception {
        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        UserEntity savedUser = userService.createUser(testCreateUserDtoA);
        ApiUserDetails apiUserDetails = new ApiUserDetails(savedUser);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/user-profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(apiUserDetails))
                        .requestAttr("userId", 1L)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(apiUserDetails.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(apiUserDetails.getUser().getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(apiUserDetails.getUsername())
        );
    }

    @Test
    public void testThatGetUserProfileReturnsHttp403WhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/user-profile")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );
    }

    @Test
    public void testThatGetUserProfileReturnsHttp404WhenUserDoesNotExists() throws Exception {
        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        UserEntity savedUser = userService.createUser(testCreateUserDtoA);
        ApiUserDetails apiUserDetails = new ApiUserDetails(savedUser);

        long notFoundUserId = 999L;

        mockMvc.perform(
                MockMvcRequestBuilders.get("/user-profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(apiUserDetails))
                        .requestAttr("userId", notFoundUserId)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatUpdateUserProfileReturnsHttp200Ok() throws Exception {
        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();

        UserEntity savedUser = userService.createUser(testCreateUserDtoA);
        ApiUserDetails apiUserDetails = new ApiUserDetails(savedUser);

        UpdateUserProfileDto updateUserProfileDto = TestDataUtils.createUpdateUserProfileDto();

        String updateUserProfileJson = objectMapper.writeValueAsString(updateUserProfileDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/user-profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserProfileJson)
                        .with(SecurityMockMvcRequestPostProcessors.user(apiUserDetails))
                        .requestAttr("userId", 1L)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatUpdateUserProfileReturnsUpdatedUserProfile() throws Exception {
        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        UserEntity savedUser = userService.createUser(testCreateUserDtoA);
        ApiUserDetails apiUserDetails = new ApiUserDetails(savedUser);

        UpdateUserProfileDto updateUserProfileDto = TestDataUtils.createUpdateUserProfileDto();
        updateUserProfileDto.setName("UPDATED");
        updateUserProfileDto.setEmail("updated@email.com");

        String updateUserProfileJson = objectMapper.writeValueAsString(updateUserProfileDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/user-profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserProfileJson)
                        .with(SecurityMockMvcRequestPostProcessors.user(apiUserDetails))
                        .requestAttr("userId", 1L)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("updated@email.com")
        );
    }

    @Test
    public void testThatUpdateUserProfileReturnsHttp403WhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/user-profile")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );
    }

    @Test
    public void testThatUpdateUserProfileReturnsHttp404WhenUserDoesNotExists() throws Exception {
        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        UserEntity savedUser = userService.createUser(testCreateUserDtoA);
        ApiUserDetails apiUserDetails = new ApiUserDetails(savedUser);

        UpdateUserProfileDto updateUserProfileDto = TestDataUtils.createUpdateUserProfileDto();

        String updateUserProfileJson = objectMapper.writeValueAsString(updateUserProfileDto);

        long notFoundUserId = 999L;

        mockMvc.perform(
                MockMvcRequestBuilders.put("/user-profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserProfileJson)
                        .with(SecurityMockMvcRequestPostProcessors.user(apiUserDetails))
                        .requestAttr("userId", notFoundUserId)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatUpdateUserProfileReturnsHttp409WhenOtherUserHasTheSameEmail() throws Exception {
        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        testCreateUserDtoA.setEmail("firstuser@email.com");

        UserEntity savedUser = userService.createUser(testCreateUserDtoA);
        ApiUserDetails apiUserDetails = new ApiUserDetails(savedUser);

        CreateUserDto testCreateUserDtoB = TestDataUtils.createTestCreateUserDtoA();
        testCreateUserDtoB.setEmail("seconduser@email.com");

        UserEntity savedSecondUser = userService.createUser(testCreateUserDtoB);

        UpdateUserProfileDto updateUserProfileDto = TestDataUtils.createUpdateUserProfileDto();
        updateUserProfileDto.setEmail(savedSecondUser.getEmail());

        String updateUserProfileJson = objectMapper.writeValueAsString(updateUserProfileDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/user-profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserProfileJson)
                        .with(SecurityMockMvcRequestPostProcessors.user(apiUserDetails))
                        .requestAttr("userId", 1L)
        ).andExpect(
                MockMvcResultMatchers.status().isConflict()
        );
    }
}
