package pedrk72.quarkusSocial.rest;

import pedrk72.quarkusSocial.rest.dto.CreateUserRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource  {

    @POST
    @Consumes(MediaType.APPLICATION_JSON) //Which type of data the method will receive
    @Produces(MediaType.APPLICATION_JSON) //Which type of data the method will return
    public Response createUser(CreateUserRequest userRequest){
        return Response.ok(userRequest).build();
    }

    @GET
    public Response listAllUsers(){
        return Response.ok().build();
    }
}
