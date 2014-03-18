package com.sopovs.moradanen.zkoss;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;
import org.zkoss.zk.ui.http.HttpSessionListener;

/**
 * @author isopov
 * @since 18.03.2014
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class TestApplication {

	@Bean
	public HttpSessionListener zkossSessionListener() {
		return new HttpSessionListener();
	}

	@Bean
	public ServletRegistrationBean zkLoader() {
		ServletRegistrationBean zkLoader = new ServletRegistrationBean(new DHtmlLayoutServlet(), "*.zul", "*.zhtml");
		zkLoader.getInitParameters().put("update-uri", "/zkau");
		return zkLoader;
	}

	@Bean
	public ServletRegistrationBean auEngine() {
		ServletRegistrationBean auEngine = new ServletRegistrationBean(new DHtmlUpdateServlet(), "/zkau/*");
		return auEngine;
	}


	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

}
