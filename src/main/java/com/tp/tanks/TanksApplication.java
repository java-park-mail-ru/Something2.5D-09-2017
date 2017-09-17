package com.tp.tanks;

import com.tp.tanks.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;

@SpringBootApplication
public class TanksApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(TanksApplication.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(TanksApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

		System.out.println("Creating tables");

//		jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users(" +
				"id SERIAL, username VARCHAR(255), email VARCHAR(255), password VARCHAR(255))");

		System.out.println("table created");
	}
}

//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
//
//			System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}
//
//		};
//	}


