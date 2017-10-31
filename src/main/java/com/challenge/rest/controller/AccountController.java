package com.challenge.rest.controller;

import com.challenge.domain.service.account.AccountDetail;
import com.challenge.domain.service.account.AccountRequest;
import com.challenge.domain.service.account.AccountService;
import com.challenge.domain.shared.exceptions.AccountNotFoundException;
import com.challenge.domain.shared.exceptions.DomainException;
import com.challenge.rest.model.account.AccountView;
import com.challenge.rest.model.account.CreateAccountModel;
import com.challenge.rest.model.shared.MonetaryAmountModel;
import java.math.BigDecimal;
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

@Path("account")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

  @Inject
  private AccountService accountService;
  @Inject
  private ModelMapper modelMapper;
  @Context
  private UriInfo uriInfo;

  @POST
  public Response create(@NotNull final CreateAccountModel model) {
    AccountRequest accountRequest = new AccountRequest();
    modelMapper.map(model, accountRequest);
    AccountDetail accountDetail;
    try {
      accountDetail = accountService.create(accountRequest);
    } catch (DomainException e) {
      return Response.status(Status.NOT_FOUND).build();
    }
    URI resourceLocation = uriInfo.getAbsolutePathBuilder()
        .path(String.valueOf(accountDetail.getIban()))
        .build();
    return Response.created(resourceLocation).build();

  }

  @GET
  @Path("{iban}")
  public Response get(@NotNull @PathParam("iban") final String iban) {
    AccountDetail accountDetail;
    try {
      accountDetail = accountService.get(iban);
    } catch (AccountNotFoundException e) {
      return Response.status(Status.NOT_FOUND).build();
    }
    AccountView accountModel = mapResult(accountDetail);

    return Response.ok(accountModel).build();
  }

  private AccountView mapResult(AccountDetail accountDetail) {
    AccountView accountModel = new AccountView();
    accountModel.setUserId(accountDetail.getUser().getId());
    accountModel.setIban(accountDetail.getIban());
    BigDecimal amount = accountDetail.getBalance().getNumber().numberValue(BigDecimal.class);
    accountModel.setBalance(new MonetaryAmountModel(amount,
        accountDetail.getBalance().getCurrency().getCurrencyCode()));
    accountModel.setLastMovement(accountDetail.getLastMovement());
    return accountModel;
  }


}
