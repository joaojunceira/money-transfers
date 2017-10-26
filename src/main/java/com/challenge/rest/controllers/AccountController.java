package com.challenge.rest.controllers;

import com.challenge.domain.model.Account;
import com.challenge.domain.services.AccountRequestSpecification;
import com.challenge.rest.model.AccountModel;
import com.challenge.domain.services.AccountService;
import com.challenge.rest.model.CreateAccountModel;
import java.net.URI;
import java.util.Currency;
import javax.inject.Inject;
import javax.validation.constraints.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.ValidationException;
import org.modelmapper.ModelMapper;

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
    Account account = _accountService.get(iban);
    if (account != null) {
      AccountModel model = new AccountModel();
      _modelMapper.map(account, model);
      return Response.ok(model).build();
    }
    return Response.status(Response.Status.NOT_FOUND).build();
  }

  @POST
  public Response create(CreateAccountModel model)
      throws ValidationException {
    AccountRequestSpecification specification = new AccountRequestSpecification();
    specification.setCurrency(Currency.getInstance(model.getCurrency()));
    specification.setUserId(model.getUserId());
    Boolean isCreated = _accountService.create(specification);
    if (isCreated) {
      URI newResource = UriBuilder.fromMethod(AccountController.class, "get").build();
      return Response.created(newResource).build();
    }
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
  }

}
