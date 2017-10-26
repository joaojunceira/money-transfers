package com.challenge.rest.controllers;

import com.challenge.domain.services.TransferRequest;
import com.challenge.domain.services.TransferResult;
import com.challenge.rest.model.RequestTransferModel;
import com.challenge.domain.services.TransferService;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.modelmapper.ModelMapper;

@Path("transfer")
public class TransferController {

  @Inject
  private TransferService transferService;
  @Inject
  private ModelMapper modelMapper;

  @POST
  public Response doTransfer(RequestTransferModel body) {
    TransferRequest request = new TransferRequest();
    modelMapper.map(body, request);
    TransferResult result = transferService.doTransfer(request);
    if (result.getSuccessful()){
      return Response.ok().build();
    }
    return Response.status(Status.UNAUTHORIZED).build();
  }
}
