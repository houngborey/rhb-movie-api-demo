package kh.dev.scott.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = { ReactiveUserDetailsServiceAutoConfiguration.class, R2dbcAutoConfiguration.class })
public class SpringReactiveMovieApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveMovieApplication.class, args);

	}

}
