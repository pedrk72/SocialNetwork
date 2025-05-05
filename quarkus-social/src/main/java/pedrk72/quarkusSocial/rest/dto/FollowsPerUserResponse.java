package pedrk72.quarkusSocial.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class FollowsPerUserResponse {
    private Integer followersCount;
    private List<FollowerResponse> followers;
}
