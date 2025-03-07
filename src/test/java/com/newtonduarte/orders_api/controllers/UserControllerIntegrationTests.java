package com.newtonduarte.orders_api.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.newtonduarte.orders_api.TestDataUtils;
import com.newtonduarte.orders_api.domain.entities.UserEntity;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {
    private final MockMvc mockMvc;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserControllerIntegrationTests(MockMvc mockMvc, UserService userService) {
        this.mockMvc = mockMvc;
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateUserReturnsHttp201Created() throws Exception {
        UserEntity testUserEntityA = TestDataUtils.createTestUserEntityA();
        String userJson = objectMapper.writeValueAsString(testUserEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateUserReturnsSavedUser() throws Exception {
        UserEntity testUserEntityA = TestDataUtils.createTestUserEntityA();
        String userJson = objectMapper.writeValueAsString(testUserEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testUserEntityA.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(testUserEntityA.getEmail())
        );
    }

    @Test
    public void testThatGetUsersReturnsHttp200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetUsersReturnsListOfUsers() throws Exception {
        UserEntity testUserEntityA = TestDataUtils.createTestUserEntityA();
        UserEntity savedUserEntity = userService.save(testUserEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").value(savedUserEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(savedUserEntity.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].email").value(savedUserEntity.getEmail())
        );
    }

    @Test
    public void testThatGetUserReturnsHttp200WhenUserExist() throws Exception {
        UserEntity testUserEntityA = TestDataUtils.createTestUserEntityA();
        UserEntity savedUser = userService.save(testUserEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetUserReturnsHttp404WhenUserDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetUserReturnsUserWhenUserExist() throws Exception {
        UserEntity testUserEntityA = TestDataUtils.createTestUserEntityA();
        UserEntity savedUserEntity = userService.save(testUserEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + savedUserEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedUserEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(savedUserEntity.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(savedUserEntity.getEmail())
        );
    }

    @Test
    public void testThatUpdateUserReturnsHttp200WhenUserExist() throws Exception {
        UserEntity testUserEntityA = TestDataUtils.createTestUserEntityA();
        UserEntity savedUserEntity = userService.save(testUserEntityA);

        String userJson = objectMapper.writeValueAsString(savedUserEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + savedUserEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatUpdateUserReturnsHttp404WhenUserDoesNotExist() throws Exception {
        UserEntity testUserEntityA = TestDataUtils.createTestUserEntityA();
        String userJson = objectMapper.writeValueAsString(testUserEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatUpdateUserUpdatesExistingUser() throws Exception {
        UserEntity testUserEntityA = TestDataUtils.createTestUserEntityA();
        UserEntity savedUserEntity = userService.save(testUserEntityA);

        savedUserEntity.setName("UPDATED");
        savedUserEntity.setEmail("updated@email.com");

        String userJson = objectMapper.writeValueAsString(savedUserEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + savedUserEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedUserEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("updated@email.com")
        );
    }

    @Test
    public void testThatPartialUpdateUserReturnsHttp200WhenUserExists() throws Exception {
        UserEntity testUserEntityA = TestDataUtils.createTestUserEntityA();
        UserEntity savedUserEntity = userService.save(testUserEntityA);

        savedUserEntity.setName("UPDATED");

        String userJson = objectMapper.writeValueAsString(savedUserEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/" + savedUserEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateUserReturnsHttp404WhenUserDoesNotExists() throws Exception {
        UserEntity testUserEntityA = TestDataUtils.createTestUserEntityA();
        String userJson = objectMapper.writeValueAsString(testUserEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateUserUpdatesExistingUser() throws Exception {
        UserEntity testUserEntityA = TestDataUtils.createTestUserEntityA();
        UserEntity savedUserEntity = userService.save(testUserEntityA);

        savedUserEntity.setName("UPDATED");

        String userJson = objectMapper.writeValueAsString(savedUserEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/" + savedUserEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedUserEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(savedUserEntity.getEmail())
        );
    }

    @Test
    public void testThatDeleteUserReturnsHttp404WhenUserDoesNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatDeleteUserReturnsHttp204WhenUserExists() throws Exception {
        UserEntity testUserEntityA = TestDataUtils.createTestUserEntityA();
        UserEntity savedUserEntity = userService.save(testUserEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/" + savedUserEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
