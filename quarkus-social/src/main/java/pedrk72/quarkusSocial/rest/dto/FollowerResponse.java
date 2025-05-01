package pedrk72.quarkusSocial.rest.dto;

import lombok.Data;
import pedrk72.quarkusSocial.domain.model.Follower;

@Data
public class FollowerResponse {
    private Long id;
    private String name;

    public FollowerResponse() {
    }

    public FollowerResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public FollowerResponse(Follower follower) {
        this(follower.getId(), follower.getFollower().getName());
    }
}
