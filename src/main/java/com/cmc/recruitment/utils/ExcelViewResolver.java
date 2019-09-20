package com.cmc.recruitment.utils;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class ExcelViewResolver implements ViewResolver {
  @Override
  public View resolveViewName(String s, Locale locale) throws Exception {

    return new ExcelView();
  }
}
