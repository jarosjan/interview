package interview.jj.model;

import java.util.UUID;

public record ProjectUpdateRequest(
        UUID id,
        String name
) {
}
