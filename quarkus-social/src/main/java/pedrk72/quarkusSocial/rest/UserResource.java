package pedrk72.quarkusSocial.rest;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import pedrk72.quarkusSocial.domain.model.User;
import pedrk72.quarkusSocial.domain.repository.UserRepository;
import pedrk72.quarkusSocial.rest.dto.CreateUserRequest;
import pedrk72.quarkusSocial.rest.dto.ResponseError;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/users")
public class UserResource  {

    private UserRepository repository;
    private Validator validator;

    //Constructor to add dependencies
    @Inject
    public UserResource(UserRepository repository, Validator validator){
        this.repository = repository;
        this.validator = validator;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON) //Which type of data the method will receive
    @Produces(MediaType.APPLICATION_JSON) //Which type of data the method will return
    @Transactional
    public Response createUser(CreateUserRequest userRequest){

        Set<ConstraintViolation<CreateUserRequest>> validate = validator.validate(userRequest);

//        if (!validate.isEmpty()){
//            ConstraintViolation<CreateUserRequest> respectiveError = validate.stream().findAny().get();
//
//            String errorMessage = respectiveError.getMessage();
//
//            return Response.status(400).entity(errorMessage).build();
//        }

        if (!validate.isEmpty()){
            ResponseError responseError = ResponseError.createFromValidation(validate);

            return Response.status(400).entity(responseError).build();
        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());

        repository.persist(user);

        //return Response.ok(user).build();
        return  Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(user)
                .build();
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
            return Response.noContent().build();
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
