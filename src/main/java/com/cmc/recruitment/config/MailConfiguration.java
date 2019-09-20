/**
 * 
 */
package com.cmc.recruitment.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @description: .
 * @author: NHPhong
 * @created_date: Mar 2, 2018
 * @modifier: User
 * @modifier_date: Mar 2, 2018
 */
@Configuration
public class MailConfiguration {
  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);
    mailSender.setUsername("recruitmentcmcglobal@gmail.com");
    mailSender.setPassword("cmcglobal1");
    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    return mailSender;
  }
}
