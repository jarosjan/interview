package interview.jj.controller;

import interview.jj.annotation.AuthenticatedEmail;
import interview.jj.model.ProjectCreateRequest;
import interview.jj.model.ProjectResponse;
import interview.jj.model.ProjectUpdateRequest;
import interview.jj.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ProjectResponse> createProject(@AuthenticatedEmail String email, @RequestBody ProjectCreateRequest request) {
        log.info("Creating project with name: {}", request.name());
        return new ResponseEntity<>(projectService.createProject(email, request), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all projects")
    @GetMapping("/all")
    public ResponseEntity<List<ProjectResponse>> getProjects(@AuthenticatedEmail String email) {
        log.info("Getting all projects for user with email: {}", email);
        return new ResponseEntity<>(projectService.getProjects(email), HttpStatus.OK);
    }

    @Operation(summary = "Get project by id")
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(@AuthenticatedEmail String email, @PathVariable UUID projectId) {
        log.info("Getting project with projectId: {}", projectId);
        return new ResponseEntity<>(projectService.getProject(email, projectId), HttpStatus.OK);
    }

    @Operation(summary = "Update project")
    @PatchMapping
    public ResponseEntity<ProjectResponse> updateProject(@AuthenticatedEmail String email, @RequestBody ProjectUpdateRequest request) {
        log.info("Updating project with projectId: {}", request.id());
        return new ResponseEntity<>(projectService.updateProject(email, request), HttpStatus.OK);
    }

    @Operation(summary = "Delete project")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@AuthenticatedEmail String email, @PathVariable UUID projectId) {
        log.info("Deleting project with projectId: {}", projectId);
        projectService.deleteProject(email, projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
