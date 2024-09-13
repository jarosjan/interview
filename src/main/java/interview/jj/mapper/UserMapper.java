package interview.jj.mapper;

import interview.jj.entity.TbUserEntity;
import interview.jj.model.ProjectResponse;
import interview.jj.model.UserResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public TbUserEntity toEntity(String name, String password, String email) {
        return TbUserEntity.builder()
                .name(name)
                .password(password)
                .email(email)
                .build();
    }

    public UserResponse toResponse(TbUserEntity user) {
        return new UserResponse(
                user.getName(),
                user.getEmail(),
                user.getProjects().stream().map(p -> new ProjectResponse(p.getId(), p.getName())).toList()
        );
    }

}
