package pedrk72.quarkusSocial.rest;


import io.quarkus.hibernate.orm.panache.PanacheQuery;

import pedrk72.quarkusSocial.domain.model.Post;
import pedrk72.quarkusSocial.domain.model.User;
import pedrk72.quarkusSocial.domain.repository.FollowerRepository;
import pedrk72.quarkusSocial.domain.repository.PostRepository;
import pedrk72.quarkusSocial.domain.repository.UserRepository;
import pedrk72.quarkusSocial.rest.dto.CreatePostRequest;
import pedrk72.quarkusSocial.rest.dto.PostResponse;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users/{userId}/posts")
//Those annotations are there because every method of that class consumes and produces json files,
//otherwise those annotations should be above of each method
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FollowerRepository followerRepository;

    @Inject
    public PostResource(UserRepository userRepository, PostRepository postRepository, FollowerRepository followerRepository){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.followerRepository = followerRepository;
    }

    //@PathParam() used to set the parameter that comes in URL
    @POST
    @Transactional
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest postRequest){
        User byId = userRepository.findById(userId);
        Post post = new Post();

        if (byId == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        post.setPostText(postRequest.getPostText());
        post.setDateTime(LocalDateTime.now());
        post.setUser(byId);
        postRepository.persist(post);

        return Response.accepted(Response.Status.CREATED).build();
    }

    @GET
    public Response listPosts(@PathParam("userId") Long userId,
                              @HeaderParam("followerId") Long followerId){
        User byId = userRepository.findById(userId);

        if (byId == null){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        if (followerId == null){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("You forgot to indicate the follower id")
                    .build();
        }

        User follower = userRepository.findById(followerId);

        if (follower == null){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("The respective follower does not exist")
                    .build();
        }

        boolean follows = followerRepository.follows(follower, byId);

        if (!follows){
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity("You can not see these posts")
                    .build();
        }


        // Two ways of do the same thing
        PanacheQuery<Post> posts = postRepository.find("user", byId);
        //PanacheQuery<Post> posts = postRepository.find("select * from Post where user = :user");
        var listOfPosts = posts.list();

//        var postResponseList = listOfPosts.stream()
//                .map(post -> PostResponse.fromEntity(post))
//                .collect(Collectors.toList());

        List<PostResponse> postResponseList = listOfPosts.stream()
                .map(PostResponse::fromEntity)
                .collect(Collectors.toList());

        return Response.ok(postResponseList).build();
    }
}
