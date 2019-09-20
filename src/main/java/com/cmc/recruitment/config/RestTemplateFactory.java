package com.cmc.recruitment.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RestTemplateFactory {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Bean
	public RestTemplate createRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(
										HttpClientBuilder.create()
											.setMaxConnTotal(1200)
											.setMaxConnPerRoute(300)
											.build()));
		
		List<HttpMessageConverter<?>> converters = new ArrayList<>();
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
		List<HttpMessageConverter<?>> preConverters = restTemplate.getMessageConverters();
		
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		stringConverter.setSupportedMediaTypes(Arrays.asList(
				MediaType.TEXT_PLAIN,
				MediaType.TEXT_HTML,
				MediaType.APPLICATION_JSON));

		converters.add(stringConverter);	// Must be first
		converters.add(jsonConverter);		// Must be second
		converters.addAll(preConverters);

		restTemplate.setMessageConverters(converters);
		
		return restTemplate;
	}
	
}