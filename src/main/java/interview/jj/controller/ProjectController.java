package interview.jj.controller;

import interview.jj.annotation.AuthenticatedEmail;
import interview.jj.model.ProjectCreateRequest;
import interview.jj.model.ProjectResponse;
import interview.jj.model.ProjectUpdateRequest;
import interview.jj.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(summary = "Create project")
    @PostMapping
    public void createProject(@AuthenticatedEmail String email, @RequestBody ProjectCreateRequest request) {
        log.info("Creating project with name: {}", request.name());
        projectService.createProject(email, request);
    }

    @Operation(summary = "Get all projects")
    @GetMapping("/all")
    public List<ProjectResponse> getProjects(@AuthenticatedEmail String email) {
        log.info("Getting all projects for user with email: {}", email);
        return projectService.getProjects(email);
    }

    @Operation(summary = "Get project by id")
    @GetMapping
    public ProjectResponse getProject(@AuthenticatedEmail String email, @RequestParam UUID projectId) {
        log.info("Getting project with projectId: {}", projectId);
        return projectService.getProject(email, projectId);
    }

    @Operation(summary = "Update project")
    @PutMapping
    public ProjectResponse updateProject(@AuthenticatedEmail String email, @RequestBody ProjectUpdateRequest request) {
        log.info("Updating project with projectId: {}", request.id());
        return projectService.updateProject(email, request);
    }

    @Operation(summary = "Delete project")
    @DeleteMapping
    public void deleteProject(@AuthenticatedEmail String email, @RequestParam UUID projectId) {
        log.info("Deleting project with projectId: {}", projectId);
        projectService.deleteProject(email, projectId);
    }

}
