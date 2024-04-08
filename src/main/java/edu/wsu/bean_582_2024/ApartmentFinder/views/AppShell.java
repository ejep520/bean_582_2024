package edu.wsu.bean_582_2024.ApartmentFinder.views;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.AppShellSettings;

public class AppShell implements AppShellConfigurator {
  @Override
  public void configurePage(AppShellSettings settings) {
    settings.setViewport("width=device-width, initial-scale=1");
    settings.setBodySize("100vw", "100vh");
    settings.addFavIcon("icon", "data:,", "0x0");
    
  } 
  
}
