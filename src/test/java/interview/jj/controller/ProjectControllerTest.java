package interview.jj.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import interview.jj.BaseWebTest;
import interview.jj.model.ProjectCreateRequest;
import interview.jj.model.ProjectResponse;
import interview.jj.model.ProjectUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectControllerTest extends BaseWebTest {

    @Test
    void testCreateProjectAndGetProject() throws Exception {
        mockMvc.perform(post("/project")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(
                                new ProjectCreateRequest("testProject"))))
                .andExpect(status().isOk());

        assertEquals(1, tbProjectRepository.findAll().size());

        MvcResult results = mockMvc.perform(get("/project/all")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk()).andReturn();

        List<ProjectResponse> responses = objectMapper.readValue(
                results.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        assertEquals(1, responses.size());
        assertEquals("testProject", responses.getFirst().name());

        MvcResult singleResult = mockMvc.perform(get("/project?projectId=" + responses.getFirst().projectId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk()).andReturn();

        ProjectResponse response = objectMapper.readValue(singleResult.getResponse().getContentAsString(), ProjectResponse.class);

        assertEquals("testProject", response.name());
    }

    @Test
    void testCreateProjectAndDelete() throws Exception {
        mockMvc.perform(post("/project")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(
                                new ProjectCreateRequest("testProject"))))
                .andExpect(status().isOk());

        assertEquals(1, tbProjectRepository.findAll().size());

        MvcResult results = mockMvc.perform(get("/project/all")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk()).andReturn();

        List<ProjectResponse> responses = objectMapper.readValue(
                results.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        mockMvc.perform(delete("/project?projectId=" + responses.getFirst().projectId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());

        assertEquals(0, tbProjectRepository.findAll().size());
    }

    @Test
    void testUpdateProject() throws Exception {
        mockMvc.perform(post("/project")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(
                                new ProjectCreateRequest("testProject"))))
                .andExpect(status().isOk());

        assertEquals(1, tbProjectRepository.findAll().size());

        MvcResult results = mockMvc.perform(get("/project/all")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk()).andReturn();

        List<ProjectResponse> responses = objectMapper.readValue(
                results.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        mockMvc.perform(put("/project")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(
                                new ProjectUpdateRequest(responses.getFirst().projectId(), "updatedProject"))))
                .andExpect(status().isOk());

        MvcResult singleResult = mockMvc.perform(get("/project?projectId=" + responses.getFirst().projectId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk()).andReturn();

        ProjectResponse response = objectMapper.readValue(singleResult.getResponse().getContentAsString(), ProjectResponse.class);

        assertEquals("updatedProject", response.name());
    }

    @Test
    void testComplexResourceOperationWithMultipleUsers() throws Exception {
        String secondToken = createUser("testUser2", "password", "user2@example.com");

        mockMvc.perform(post("/project")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(
                                new ProjectCreateRequest("testProject"))))
                .andExpect(status().isOk());

        mockMvc.perform(post("/project")
                        .header("Authorization", "Bearer " + secondToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(
                                new ProjectCreateRequest("testProject2"))))
                .andExpect(status().isOk());

        assertEquals(2, tbProjectRepository.findAll().size());

        MvcResult results = mockMvc.perform(get("/project/all")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk()).andReturn();

        List<ProjectResponse> responses = objectMapper.readValue(
                results.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        assertEquals(1, responses.size());
        assertEquals("testProject", responses.getFirst().name());

        MvcResult secondResults = mockMvc.perform(get("/project/all")
                        .header("Authorization", "Bearer " + secondToken))
                .andExpect(status().isOk()).andReturn();

        List<ProjectResponse> secondResponses = objectMapper.readValue(
                secondResults.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        assertEquals(1, secondResponses.size());
        assertEquals("testProject2", secondResponses.getFirst().name());

        mockMvc.perform(delete("/project?projectId=" + responses.getFirst().projectId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());

        assertEquals(1, tbProjectRepository.findAll().size());

        mockMvc.perform(delete("/project?projectId=" + secondResponses.getFirst().projectId())
                        .header("Authorization", "Bearer " + secondToken))
                .andExpect(status().isOk());

        assertEquals(0, tbProjectRepository.findAll().size());
    }

}
