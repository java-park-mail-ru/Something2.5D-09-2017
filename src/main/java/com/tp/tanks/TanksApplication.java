package com.tp.tanks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TanksApplication {

	public static void main(String[] args) {
		SpringApplication.run(TanksApplication.class, args);
	}
}








//	@Override
//	public void run(String... strings) throws Exception {
//
//		System.out.println("Creating tables");
//
////		jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
//		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users(" +
//				"id SERIAL, username VARCHAR(255), email VARCHAR(255), password VARCHAR(255))");
//
//		System.out.println("table created");
//	}
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


