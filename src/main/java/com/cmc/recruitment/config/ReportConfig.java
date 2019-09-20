package com.cmc.recruitment.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import com.cmc.recruitment.utils.ExcelViewAMResolver;
import com.cmc.recruitment.utils.ExcelViewDuReportResolver;
import com.cmc.recruitment.utils.ExcelViewResolver;

@Configuration
public class ReportConfig extends WebMvcConfigurerAdapter {
  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.defaultContentType(MediaType.APPLICATION_JSON).favorPathExtension(true);
  }

  /*
   * Configure ContentNegotiatingViewResolver
   */
  @Bean
  public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
    ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
    resolver.setContentNegotiationManager(manager);

    // Define all possible view resolvers
    List<ViewResolver> resolvers = new ArrayList<>();

    resolvers.add(excelViewResolver());
    resolvers.add(excelViewAMResolver());
    resolvers.add(excelViewDuResolver());
    resolver.setViewResolvers(resolvers);
    return resolver;
  }

  /*
   * Configure View resolver to provide XLS output using Apache POI library to
   * generate XLS output for an object content
   */
  @Bean
  public ViewResolver excelViewResolver() {
    return new ExcelViewResolver();
  }
  
  /*
   * Configure View resolver to provide XLS output using Apache POI library to
   * generate XLS output for an object content
   */
  @Bean
  public ViewResolver excelViewAMResolver() {
    return (ViewResolver) new ExcelViewAMResolver();
  }
  
  /*
   * Configure View resolver to provide XLS output using Apache POI library to
   * generate XLS output for an object content
   */
  @Bean
  public ViewResolver excelViewDuResolver() {
    return new ExcelViewDuReportResolver();
  }
}
