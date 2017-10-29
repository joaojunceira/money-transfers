package com.challenge.rest.controller;

import com.challenge.domain.service.account.AccountService;
import com.challenge.rest.model.CreateAccountModel;
import javax.inject.Inject;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.ValidationException;
import org.modelmapper.ModelMapper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Path("account")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

  @Inject
  private AccountService accountService;
  @Inject
  private ModelMapper modelMapper;

  @POST
  public Response create(CreateAccountModel model)
      throws ValidationException {
    throw new NotImplementedException();
  }

}
