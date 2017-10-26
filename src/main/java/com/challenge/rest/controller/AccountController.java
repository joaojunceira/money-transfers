package com.challenge.rest.controller;

import com.challenge.domain.service.account.AccountService;
import com.challenge.rest.model.CreateAccountModel;
import javax.inject.Inject;
import javax.validation.constraints.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.xml.bind.ValidationException;
import org.modelmapper.ModelMapper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Path("account")
public class AccountController {

  @Inject
  private AccountService _accountService;
  @Inject
  private ModelMapper _modelMapper;

  @GET
  public Response get(
      @Pattern(message = "Invalid IBAN", regexp = "^[A-Z]{2}\\d{2}(?:\\d{4}){3}\\d{4}(?:\\d\\d?)?$")
      @PathParam("iban") String iban)
      throws ValidationException {
    throw new NotImplementedException();
  }

  @POST
  public Response create(CreateAccountModel model)
      throws ValidationException {
    throw new NotImplementedException();
  }

}
