package interview.jj.model;

import java.util.UUID;

public record ProjectResponse(
        UUID projectId,
        String name
) {
}
