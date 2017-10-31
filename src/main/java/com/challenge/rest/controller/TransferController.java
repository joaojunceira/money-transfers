package com.challenge.rest.controller;

import com.challenge.domain.service.transfer.TransferRequest;
import com.challenge.domain.service.transfer.TransferResult;
import com.challenge.domain.service.transfer.TransferService;
import com.challenge.domain.shared.exceptions.AccountNotFoundException;
import com.challenge.domain.shared.exceptions.CurrencyNotSupportedException;
import com.challenge.domain.shared.exceptions.NotEnoughFundsException;
import com.challenge.rest.model.shared.MessageModel;
import com.challenge.rest.model.shared.MonetaryAmountModel;
import com.challenge.rest.model.transfer.RequestTransferModel;
import com.challenge.rest.model.transfer.TransferView;
import java.math.BigDecimal;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.modelmapper.ModelMapper;

@Path("transfer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransferController {

  @Inject
  private TransferService transferService;
  @Inject
  private ModelMapper modelMapper;

  @POST
  public Response doTransfer(@NotNull RequestTransferModel body) {
    TransferRequest request = new TransferRequest();
    modelMapper.map(body, request);
    request.setAmount(body.getAmount().getAmount());
    request.setCurrency(body.getAmount().getCurrency());
    try {
      TransferResult result = transferService.doTransfer(request);
      TransferView transferView = getTransferView(result);
      return Response.status(Status.OK).entity(transferView).build();
    } catch (CurrencyNotSupportedException | AccountNotFoundException e) {
      return Response.status(Status.BAD_REQUEST).entity(new MessageModel(e.getMessage())).build();
    } catch (NotEnoughFundsException e) {
      return Response.status(Status.FORBIDDEN).entity(new MessageModel(e.getMessage())).build();
    }
  }

  private TransferView getTransferView(TransferResult result) {
    TransferView transferView = new TransferView();
    transferView.setId(result.getId());
    BigDecimal amount = result.getAmount().getNumber().numberValue(BigDecimal.class);
    String currencyCode = result.getAmount().getCurrency().getCurrencyCode();
    transferView.setAmount(new MonetaryAmountModel(amount, currencyCode));
    transferView.setSource(result.getSource());
    transferView.setDestination(result.getDestination());
    return transferView;
  }
}
