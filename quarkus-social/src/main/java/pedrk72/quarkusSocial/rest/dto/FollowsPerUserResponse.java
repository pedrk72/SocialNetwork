package pedrk72.quarkusSocial.rest.dto;

import lombok.Data;
import pedrk72.quarkusSocial.domain.model.Follower;

import java.util.List;

@Data
public class FollowsPerUserResponse {
    private Integer followersCount;
    private List<FollowerResponse> followers;
}
