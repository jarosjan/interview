package interview.jj.controller;

import interview.jj.BaseWebTest;
import interview.jj.entity.TbUserEntity;
import interview.jj.model.ProjectCreateRequest;
import interview.jj.model.RegistrationRequest;
import interview.jj.model.UserEditRequest;
import interview.jj.model.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseWebTest {

    @Test
    void testRegisterUser() throws Exception {
        mockMvc.perform(post("/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(
                                new RegistrationRequest("testUser", "password", "testEmail@example.com"))))
                .andExpect(status().isOk());

        Optional<TbUserEntity> registeredUser = tbUserRepository.findByEmail("testEmail@example.com");

        assertTrue(registeredUser.isPresent());

        TbUserEntity user = registeredUser.get();
        assertEquals("testUser", user.getName());
        assertEquals("testEmail@example.com", user.getEmail());
    }

    @Test
    void testGetUser() throws Exception {
        MvcResult result = mockMvc.perform(get("/user")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn();

        UserResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), UserResponse.class);

        assertEquals("testUser", response.name());
        assertEquals("testUser@example.com", response.email());
    }

    @Test
    void testGetUserUnauthorized() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testUpdateUser() throws Exception {
        mockMvc.perform(put("/user")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new UserEditRequest("JJ Test"))))
                .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(get("/user")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn();

        UserResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), UserResponse.class);

        assertEquals("JJ Test", response.name());
        assertEquals("testUser@example.com", response.email());
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(post("/project")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new ProjectCreateRequest("Test Project"))))
                .andExpect(status().isOk());

        assertEquals(1, tbProjectRepository.findAll().size());

        mockMvc.perform(delete("/user")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());

        mockMvc.perform(get("/user")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().is4xxClientError());
        assertEquals(0, tbProjectRepository.findAll().size());
    }

}
