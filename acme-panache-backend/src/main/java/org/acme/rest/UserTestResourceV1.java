package org.acme.rest;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.transaction.Transactional;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.entity.ProductEntity;
import org.acme.entity.UserEntity;
import org.acme.util.panache.PanacheQueryFactory;
import org.acme.util.query.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;

@Path("/api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserTestResourceV1 {


    @POST
    @Operation(summary = "creates a new user")
    @APIResponse(responseCode = "   201", description = "user creation successful")
    @APIResponse(responseCode = "500", description = "Server unavailable")
    @Path("/")
    @Transactional
    public UserEntity create(){
        UserEntity user = new UserEntity();
        user.lastname = "haha";
        user.firstname = "hihi";
        user.email = "test@gmx.de";
        user.clientId = 1;
        user.persistAndFlush();
        return user;
    }


}
