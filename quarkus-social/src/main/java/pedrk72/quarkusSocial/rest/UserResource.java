package pedrk72.quarkusSocial.rest;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import pedrk72.quarkusSocial.domain.model.User;
import pedrk72.quarkusSocial.domain.repository.UserRepository;
import pedrk72.quarkusSocial.rest.dto.CreateUserRequest;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource  {

    private UserRepository repository;

    @Inject
    public UserResource(UserRepository repository){
        this.repository = repository;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON) //Which type of data the method will receive
    @Produces(MediaType.APPLICATION_JSON) //Which type of data the method will return
    @Transactional
    public Response createUser(CreateUserRequest userRequest){
        User user = new User();
        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());

        repository.persist(user);
        return Response.ok(user).build();
    }

    @GET
    public Response listAllUsers(){
        PanacheQuery<User> query = repository.findAll();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Transactional
    @Path("{id}")
    public Response deleteUser(@PathParam("id") Long id){
        User byId = repository.findById(id);

        if (byId != null){
            repository.delete(byId);
            return Response.ok().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Transactional
    @Path("{id}")
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userData){
        User byId = repository.findById(id);

        if (byId != null){
            byId.setName(userData.getName());
            byId.setAge(userData.getAge());
            return Response.ok(byId).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
