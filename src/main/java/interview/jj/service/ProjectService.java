package interview.jj.service;

import interview.jj.dao.TbProjectRepository;
import interview.jj.entity.TbProjectEntity;
import interview.jj.entity.TbUserEntity;
import interview.jj.exception.NotFoundException;
import interview.jj.mapper.ProjectMapper;
import interview.jj.model.ProjectCreateRequest;
import interview.jj.model.ProjectResponse;
import interview.jj.model.ProjectUpdateRequest;
import interview.jj.retrieveService.ProjectRetrievalService;
import interview.jj.retrieveService.UserRetrievalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {
    private final UserRetrievalService userRetrievalService;
    private final TbProjectRepository tbProjectRepository;
    private final ProjectRetrievalService projectRetrievalService;

    @Autowired
    public ProjectService(UserRetrievalService userRetrievalService,
                          TbProjectRepository tbProjectRepository,
                          ProjectRetrievalService projectRetrievalService) {
        this.userRetrievalService = userRetrievalService;
        this.tbProjectRepository = tbProjectRepository;
        this.projectRetrievalService = projectRetrievalService;
    }

    public ProjectResponse createProject(String email, ProjectCreateRequest request) {
        final TbUserEntity user = userRetrievalService.findByEmail(email);
        final TbProjectEntity project = ProjectMapper.toEntity(request, user);

        return ProjectMapper.toResponse(tbProjectRepository.save(project));
    }

    public List<ProjectResponse> getProjects(String email) {
        final TbUserEntity user = userRetrievalService.findByEmail(email);

        return tbProjectRepository.findByUser(user).stream()
                .map(p -> new ProjectResponse(p.getId(), p.getName()))
                .toList();
    }

    public ProjectResponse getProject(String email, UUID uuid) {
        final TbUserEntity user = userRetrievalService.findByEmail(email);

        return ProjectMapper.toResponse(projectRetrievalService.findByIdAndUser(uuid, user));
    }

    public ProjectResponse updateProject(String email, ProjectUpdateRequest request) {
        final TbUserEntity user = userRetrievalService.findByEmail(email);
        final TbProjectEntity project = projectRetrievalService.findByIdAndUser(request.id(), user);

        project.setName(request.name());
        tbProjectRepository.save(project);

        return ProjectMapper.toResponse(project);
    }

    @Transactional
    public void deleteProject(String email, UUID uuid) {
        final TbUserEntity user = userRetrievalService.findByEmail(email);

        if (!projectRetrievalService.existsByIdAndUser(uuid, user)) {
            throw new NotFoundException("Project with projectId " + uuid + " does not exist");
        }

        tbProjectRepository.deleteByIdAndUser(uuid, user);
    }

}
