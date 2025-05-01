package pedrk72.quarkusSocial.rest;

import pedrk72.quarkusSocial.domain.model.Follower;
import pedrk72.quarkusSocial.domain.repository.FollowerRepository;
import pedrk72.quarkusSocial.domain.repository.UserRepository;
import pedrk72.quarkusSocial.rest.dto.CreateFollowerRequest;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Follower API and respectives endpoints
@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

    private UserRepository userRepository;
    private FollowerRepository followerRepository;

    @Inject
    public FollowerResource(UserRepository userRepository, FollowerRepository followerRepository) {
        this.userRepository = userRepository;
        this.followerRepository = followerRepository;
    }

    @Transactional
    @PUT
    public Response followerUser(@PathParam("userId") Long userId, CreateFollowerRequest followerRequest){
        var user = userRepository.findById(userId);
        var follower = userRepository.findById(followerRequest.getFollowerId());

        if (userId.equals(followerRequest.getFollowerId())){
            return Response.status(Response.Status.CONFLICT)
                    .entity("You can not follow yourself")
                    .build();
        }

        if (user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // To verify if the follower already follows the respective user
        boolean isFollower = followerRepository.follows(follower, user);

        if (!isFollower) {
            Follower entity = new Follower();
            entity.setUser(user);
            entity.setFollower(follower);
            this.followerRepository.persist(entity);
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
