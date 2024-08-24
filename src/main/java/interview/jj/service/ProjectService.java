package interview.jj.service;

import interview.jj.dao.TbProjectRepository;
import interview.jj.dao.TbUserRepository;
import interview.jj.entity.TbProjectEntity;
import interview.jj.entity.TbUserEntity;
import interview.jj.exception.NotFoundException;
import interview.jj.model.ProjectCreateRequest;
import interview.jj.model.ProjectResponse;
import interview.jj.model.ProjectUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {
    private final TbUserRepository tbUserRepository;
    private final TbProjectRepository tbProjectRepository;

    @Autowired
    public ProjectService(TbUserRepository tbUserRepository, TbProjectRepository tbProjectRepository) {
        this.tbUserRepository = tbUserRepository;
        this.tbProjectRepository = tbProjectRepository;
    }

    public void createProject(String email, ProjectCreateRequest request) {
        final TbUserEntity user = tbUserRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " does not exist"));

        TbProjectEntity project = new TbProjectEntity();
        project.setName(request.name());
        project.setUser(user);
        project.setId(UUID.randomUUID());

        tbProjectRepository.save(project);
    }

    public List<ProjectResponse> getProjects(String email) {
        final TbUserEntity user = tbUserRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " does not exist"));

        return tbProjectRepository.findByUser(user).stream()
                .map(p -> new ProjectResponse(p.getId(), p.getName()))
                .toList();
    }

    public ProjectResponse getProject(String email, UUID uuid) {
        final TbUserEntity user = tbUserRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " does not exist"));


        return tbProjectRepository.findByIdAndUser(uuid, user)
                .map(p -> new ProjectResponse(p.getId(), p.getName()))
                .orElseThrow(() -> new NotFoundException("Project with projectId " + uuid + " does not exist"));
    }

    public ProjectResponse updateProject(String email, ProjectUpdateRequest request) {
        final TbUserEntity user = tbUserRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " does not exist"));

        final TbProjectEntity project = tbProjectRepository.findByIdAndUser(request.id(), user)
                .orElseThrow(() -> new NotFoundException("Project with projectId " + request.id() + " does not exist"));

        project.setName(request.name());
        tbProjectRepository.save(project);

        return new ProjectResponse(project.getId(), project.getName());
    }

    @Transactional
    public void deleteProject(String email, UUID uuid) {
        final TbUserEntity user = tbUserRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " does not exist"));

        tbProjectRepository.findByIdAndUser(uuid, user)
                .orElseThrow(() -> new NotFoundException("Project with projectId " + uuid + " does not exist"));

        tbProjectRepository.deleteByIdAndUser(uuid, user);
    }

}
