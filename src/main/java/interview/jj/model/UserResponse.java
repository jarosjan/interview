package interview.jj.model;

import java.util.List;

public record UserResponse(
        String name,
        String email,
        List<ProjectResponse> projects
) {
}
