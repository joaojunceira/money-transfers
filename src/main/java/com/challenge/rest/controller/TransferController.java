package com.challenge.rest.controller;

import com.challenge.domain.model.Transfer;
import com.challenge.domain.service.exceptions.AccountNotFoundException;
import com.challenge.domain.service.exceptions.CurrencyNotSupportedException;
import com.challenge.domain.service.exceptions.NotEnoughFundsException;
import com.challenge.domain.service.transfer.TransferRequest;
import com.challenge.domain.service.transfer.TransferResult;
import com.challenge.domain.service.transfer.TransferService;
import com.challenge.rest.model.MessageModel;
import com.challenge.rest.model.RequestTransferModel;
import com.challenge.rest.model.TransferResultModel;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.modelmapper.ModelMapper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Path("transfer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransferController {

  @Inject
  private TransferService transferService;
  @Inject
  private ModelMapper modelMapper;

  @POST
  public Response doTransfer(RequestTransferModel body) {
    TransferRequest request = new TransferRequest();
    modelMapper.map(body, request);
    try {
      TransferResult result = transferService.doTransfer(request);
      return Response.status(Status.OK).entity(new TransferResultModel(result.getId())).build();
    }catch (CurrencyNotSupportedException|AccountNotFoundException e){
      return Response.status(Status.BAD_REQUEST).entity(new MessageModel(e.getMessage())).build();
    }catch (NotEnoughFundsException e){
      return Response.status(Status.FORBIDDEN).entity(new MessageModel(e.getMessage())).build();
    }
  }
}
