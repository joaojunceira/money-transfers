package com.challenge.rest.controller;

import com.challenge.domain.service.transfer.TransferService;
import com.challenge.rest.model.RequestTransferModel;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.modelmapper.ModelMapper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Path("transfer")
public class TransferController {

  @Inject
  private TransferService transferService;
  @Inject
  private ModelMapper modelMapper;

  @POST
  public Response doTransfer(RequestTransferModel body) {
    throw new NotImplementedException();
  }
}
