package com.zalando.ECommerceManagement.userManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalando.ECommerceManagement.userManagement.model.User;
import com.zalando.ECommerceManagement.userManagement.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Should be able to get all the Users")
    public void testGetAllUser() throws Exception {
        List<User> mockUser = new ArrayList<>();
        User user = new User(
                1,
                "abi",
                "abi@gmail.com",
                "123456");
        mockUser.add(user);
        when(userService.getAllUsers()).thenReturn(mockUser);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("abi"))
                .andExpect(jsonPath("$[0].email").value("abi@gmail.com"))
                .andExpect(jsonPath("$[0].password").value("123456"));
    }

    @Test
    @DisplayName("Should be able to create a new User")
    public void testCreateUser() throws Exception {
        User newUser = new User(1, "kani", "kani@gmail.com", "45678");
        String jsonRequest = new ObjectMapper().writeValueAsString(newUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(userService, Mockito.times(1)).createUser(any(User.class));

    }

    @Test
    @DisplayName("Should be able to delete a user")
    public void testDeleteUserById() throws Exception {
        doNothing().when(userService).deleteUser(1);

        mockMvc.perform(delete("/users/{id}", 1))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(1);
    }

    @ParameterizedTest
    @MethodSource("getUserInput")
    @DisplayName("Should not create a new user with invalied fields")
    public void testInvalidUserFields(
            User newUser,
            String errorKey,
            String errorMessage
    ) throws Exception {
        String jsonRequest = new ObjectMapper().writeValueAsString(newUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$." + errorKey).value(errorMessage));

    }

    static Stream<Arguments> getUserInput() {
        return Stream.of(
                Arguments.of(new User(
                                null,
                                null,
                                "abi@gmail.com",
                                "12345"),
                        "name", "The Name should not be Null."),

                Arguments.of(new User(
                                null,
                                "abi",
                                null,
                                "12345"),
                        "email", "The Email should not be Null."),

                Arguments.of(new User(
                                null,
                                "abi",
                                "abi@gmail.com",
                                null),
                        "password", "The Password should not Null.")
        );


    }
}

