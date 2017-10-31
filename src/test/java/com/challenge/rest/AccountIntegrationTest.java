package com.challenge.rest;

import com.challenge.App;
import com.challenge.config.RepositoryModule;
import com.challenge.config.ServiceModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AccountIntegrationTest {

  private HttpServer server;
  private ObjectMapper objectMapper;
  private String baseUri;

  @Before
  public void setup() throws IOException {
    Injector injector = Guice.createInjector(new RepositoryModule(), new ServiceModule());
    objectMapper = new ObjectMapper();
    App app = injector.getInstance(App.class);
    server = app.startServer();
    baseUri = App.BASE_URI.concat("/account");
  }

  @Test
  public void testCreateAccount() {

  }

  @After
  public void tearDown() throws Exception {
    server.shutdownNow();
  }
}
