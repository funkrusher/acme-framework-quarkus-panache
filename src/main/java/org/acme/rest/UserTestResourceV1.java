package org.acme.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.entity.User;
import org.acme.repository.UserRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserTestResourceV1 {

    @Inject
    UserRepository userRepository;


    @POST
    @Operation(summary = "creates a new user")
    @APIResponse(responseCode = "   201", description = "user creation successful")
    @APIResponse(responseCode = "500", description = "Server unavailable")
    @Path("/")
    @Transactional
    public User create(){
        User user = new User();
        user.lastname = "haha";
        user.firstname = "hihi";
        user.email = "test@gmx.de";
        // user.setClientId(1);
        userRepository.persistAndFlush(user);
        return user;
    }


}
