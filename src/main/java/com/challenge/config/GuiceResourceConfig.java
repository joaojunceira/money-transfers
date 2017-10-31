package com.challenge.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.jvnet.hk2.guice.bridge.api.HK2IntoGuiceBridge;

public final class GuiceResourceConfig extends ResourceConfig {

  public GuiceResourceConfig(final Module... modules) {
    register(new ContainerLifecycleListener() {
      public void onStartup(Container container) {
        InjectionManager im = container.getApplicationHandler().getInjectionManager();
        ServiceLocator serviceLocator = im.getInstance(ServiceLocator.class);
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        List<Module> modulesList = new LinkedList<>();
        modulesList.add(new HK2IntoGuiceBridge(serviceLocator));
        modulesList.addAll(Arrays.asList(modules));
        Injector injector = Guice.createInjector(modulesList);
        guiceBridge.bridgeGuiceInjector(injector);
      }

      public void onReload(Container container) {
      }

      public void onShutdown(Container container) {
      }
    });
  }
}
