package hr.java.web.jeftimov.moneyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class MoneyappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyappApplication.class, args);
	}

}
