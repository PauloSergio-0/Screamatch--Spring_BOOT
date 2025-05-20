package br.com.oracleone.ScreamMatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreammatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreammatchApplication.class, args);
	}

	@Override //metodo main comum
	public void run(String... args) throws Exception {
		System.out.println("Primeiro projeto spring sem WEB test");
	}
}
