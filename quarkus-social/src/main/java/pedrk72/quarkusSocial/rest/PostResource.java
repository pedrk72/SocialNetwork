package pedrk72.quarkusSocial.rest;

import org.jboss.logging.annotations.Pos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/{userId}/posts")
//Those annotations are there because every method of that class consumes and produces json files,
//otherwise those annotations should be above of each method
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    @POST()
    public Response savePost(){
        return Response.accepted(Response.Status.ACCEPTED).build();
    }

    @GET
    public Response listPosts(){
        return Response.ok().build();
    }
}
