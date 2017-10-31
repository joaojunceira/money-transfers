package com.challenge;

import com.challenge.config.GuiceResourceConfig;
import com.challenge.config.RepositoryModule;
import com.challenge.config.ServiceModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main class.
 */
public class App {

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
    // create custom ObjectMapper

    ObjectMapper mapper = new ObjectMapper()
        .registerModule(new JavaTimeModule());
        /*.registerModule(new MoneyModule().withMonetaryAmount(Money::of))
        .enable(SerializationFeature.INDENT_OUTPUT)
        .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);*/
    // create JsonProvider to provide custom ObjectMapper
    /*JacksonJsonProvider provider = new JacksonJsonProvider()
        .configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
        .configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    provider.setMapper(mapper);*/
    JacksonJsonProvider provider = new JacksonJsonProvider();
    provider.setMapper(mapper);
    // create a resource config that scans for JAX-RS resources and providers
    // in com.challenge package
    final GuiceResourceConfig guiceResourceConfig = new GuiceResourceConfig(new RepositoryModule(),
        new ServiceModule());
    final ResourceConfig rc = new ResourceConfig(guiceResourceConfig).packages("com.challenge");
    //.register(provider);
    // create and start a new instance of grizzly http server
    // exposing the Jersey application at BASE_URI
    return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
  }
}

