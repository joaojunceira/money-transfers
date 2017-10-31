package com.challenge.rest.controller;

import com.challenge.domain.service.user.UserCreationRequest;
import com.challenge.domain.service.user.UserDetail;
import com.challenge.domain.service.user.UserService;
import com.challenge.domain.shared.exceptions.UserNotFoundException;
import com.challenge.rest.model.user.CreateUserInput;
import com.challenge.rest.model.user.UserView;
import java.net.URI;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import org.modelmapper.ModelMapper;

@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

  @Context
  UriInfo uriInfo;
  @Inject
  private UserService userService;
  @Inject
  private ModelMapper modelMapper;

  @POST
  public Response create(@NotNull CreateUserInput userModel) {
    UserCreationRequest request = new UserCreationRequest();
    modelMapper.map(userModel, request);
    UserDetail userDetail = userService.create(request);
    URI resourceLocation = uriInfo.getAbsolutePathBuilder().path(String.valueOf(userDetail.getId()))
        .build();
    return Response.created(resourceLocation).build();
  }

  @GET
  @Path("{id}")
  public Response get(@NotNull @PathParam("id") final Long id) {
    UserDetail userDetail;
    try {
      userDetail = userService.get(id);
    } catch (UserNotFoundException e) {
      return Response.status(Status.NOT_FOUND).build();
    }
    UserView view = new UserView();
    modelMapper.map(userDetail, view);
    return Response.ok(view).build();
  }
}
