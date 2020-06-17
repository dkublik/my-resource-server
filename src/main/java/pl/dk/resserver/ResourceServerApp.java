package pl.dk.resserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.UUID;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.MINUTES;

@SpringBootApplication
public class ResourceServerApp {

	public static void main(String[] args) throws Exception {
		printToken();
		SpringApplication.run(ResourceServerApp.class, args);
	}

	private static void printToken() throws Exception {
		Date issuedAt = Date.from(now());
		Date expirationDate = Date.from(now().plus(30L, MINUTES));
		String token = JwtTokenGenerator.generateToken(UUID.randomUUID().toString(), issuedAt, expirationDate);
		System.out.println("------- jwt token:");
		System.out.println(token);
	}
}
