package interview.jj.mapper;

import interview.jj.entity.TbProjectEntity;
import interview.jj.entity.TbUserEntity;
import interview.jj.model.ProjectCreateRequest;
import interview.jj.model.ProjectResponse;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class ProjectMapper {

    public TbProjectEntity toEntity(ProjectCreateRequest request, TbUserEntity user) {
        return TbProjectEntity.builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .user(user)
                .build();
    }

    public ProjectResponse toResponse(TbProjectEntity project) {
        return new ProjectResponse(
                project.getId(),
                project.getName()
        );
    }

}
