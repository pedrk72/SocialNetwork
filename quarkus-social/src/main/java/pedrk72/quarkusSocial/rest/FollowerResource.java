package pedrk72.quarkusSocial.rest;

import pedrk72.quarkusSocial.domain.model.Follower;
import pedrk72.quarkusSocial.domain.repository.FollowerRepository;
import pedrk72.quarkusSocial.domain.repository.PostRepository;
import pedrk72.quarkusSocial.domain.repository.UserRepository;
import pedrk72.quarkusSocial.rest.dto.CreateFollowerRequest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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

    public Response followerUser(@PathParam("userId") Long userId, CreateFollowerRequest followerRequest){
        var user = userRepository.findById(userId);
        var follower = userRepository.findById(followerRequest.getFollowerId());

        if (user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Follower entity = new Follower();
        entity.setUser(user);
        entity.setFollower(follower);
        followerRepository.persist(entity);

        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
