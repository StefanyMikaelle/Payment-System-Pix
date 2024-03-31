package com.stefanydev.paymentsystempix;

import com.stefanydev.paymentsystempix.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootTest
//@EnableAutoConfiguration(exclude = SecurityConfig.class) //desabilita o Spring Security
@ComponentScan(excludeFilters={@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value=SecurityConfig.class)})
class PaymentSystemPixApplicationTests {

	@Test
	void contextLoads() {
	}

}
