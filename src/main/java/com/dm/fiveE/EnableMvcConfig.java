package com.dm.fiveE;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** @Author: Chens @Date: 2020/11/1 12:35 */
@ControllerAdvice
@Configuration
public class EnableMvcConfig implements WebMvcConfigurer {

  /***
   * 静态资源放行
   * @param registry
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");
  }
}
