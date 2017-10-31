package com.challenge;

import com.challenge.config.CustomObjectMapper;
import com.challenge.config.GuiceResourceConfig;
import com.challenge.config.RepositoryModule;
import com.challenge.config.ServiceModule;
import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main class.
 */
public final class App {

  // Base URI the Grizzly HTTP server will listen on
  private static final Integer PORT = 8080;
  private static final String ENDPOINT = "http://localhost";
  public static final String BASE_URI = String.format("%s:%d", ENDPOINT, PORT);

  public static void main(String[] args) throws IOException {
    App app = new App();
    final HttpServer server = app.startServer();
    System.out.println(String.format("Jersey app started with WADL available at "
        + "%s\\application.wadl\nHit enter to stop it...", BASE_URI));
    System.in.read();
    server.shutdownNow();
  }

  /**
   * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
   *
   * @return Grizzly HTTP server.
   */
  public HttpServer startServer() throws IOException {
    final GuiceResourceConfig guiceResourceConfig = new GuiceResourceConfig(new RepositoryModule(),
        new ServiceModule());
    final ResourceConfig rc = new ResourceConfig(guiceResourceConfig).packages("com.challenge")
        .register(CustomObjectMapper.class).register(JacksonFeature.class);
    return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
  }
}

