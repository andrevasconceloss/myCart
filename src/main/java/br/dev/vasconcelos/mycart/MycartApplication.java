package br.dev.vasconcelos.mycart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class MycartApplication {

	public static Logger log = LoggerFactory.getLogger(MycartApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(MycartApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
		log.info("My Cart initialized.");
	}
}
