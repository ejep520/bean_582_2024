package edu.wsu.bean_582_2024.ApartmentFinder;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@Import(ApartmentFinderSecurity.class)
@ConfigurationProperties
public class ApartmentFinderConfiguration {

  @Bean
  public SpringResourceTemplateResolver templateResolver() {
    SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
    springResourceTemplateResolver.setPrefix("WEB-INF/views/");
    springResourceTemplateResolver.setSuffix(".html");
    springResourceTemplateResolver.setTemplateMode(TemplateMode.HTML);
    return springResourceTemplateResolver;
  }
  
  @Bean
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("messages");
    return messageSource;
  }
  
  @Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver());
    templateEngine.setTemplateEngineMessageSource(messageSource());
    return templateEngine;
  }
}
