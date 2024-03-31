package com.stefanydev.paymentsystempix;

import com.stefanydev.paymentsystempix.config.security.SecurityConfig;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {SecurityConfig.class}) //desabilita o Spring Security
//@ComponentScan(excludeFilters={@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value=SecurityConfig.class)})
@OpenAPIDefinition(info = @Info(title = "Payment System Pix", version = "1", description = "API  OpenApi Pix"))
public class PaymentSystemPixApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentSystemPixApplication.class, args);
	}

}
