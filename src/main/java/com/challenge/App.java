package com.challenge;

import com.challenge.config.RepositoryModule;
import com.challenge.config.ServiceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.net.URI;
import javax.inject.Singleton;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main class.
 */
@Singleton
public class App {

  // Base URI the Grizzly HTTP server will listen on
  private final Integer PORT = 8080;
  private final String ENDPOINT = "http://localhost";
  private final String BASE_URI = String.format("%s:%d", ENDPOINT, PORT);

  public static void main(String[] args) throws IOException {
    Injector injector = Guice.createInjector(new RepositoryModule(), new ServiceModule());
    App app = injector.getInstance(App.class);
    app.startServer();
  }

  /**
   * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
   *
   * @return Grizzly HTTP server.
   */
  public HttpServer startServer() throws IOException {
    // create a resource config that scans for JAX-RS resources and providers
    // in com.challenge package
    final ResourceConfig rc = new ResourceConfig().packages("com.challenge.rest");

    // create and start a new instance of grizzly http server
    // exposing the Jersey application at BASE_URI
    final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    System.out.println(String.format("Jersey app started with WADL available at "
        + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
    System.in.read();

    server.shutdownNow();
    return server;
  }
}

