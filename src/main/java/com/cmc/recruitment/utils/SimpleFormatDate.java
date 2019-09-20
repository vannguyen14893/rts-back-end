package com.cmc.recruitment.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class SimpleFormatDate {
  public Date formatdate(String date) throws ParseException {
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    return format.parse(date);
  }
}
