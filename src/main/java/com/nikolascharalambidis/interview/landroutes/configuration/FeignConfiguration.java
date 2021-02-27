package com.nikolascharalambidis.interview.landroutes.configuration;

import java.util.List;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import feign.codec.Decoder;

@Configuration
public class FeignConfiguration {

	@Bean
	public Decoder feignDecoder() {
		var converter = new FeignClientMessageConverter();
		ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(converter);
		return new SpringDecoder(objectFactory);
	}

	private static class FeignClientMessageConverter extends MappingJackson2HttpMessageConverter {
		public FeignClientMessageConverter(){
			setSupportedMediaTypes(List.of(MediaType.TEXT_PLAIN));
		}
	}
}
