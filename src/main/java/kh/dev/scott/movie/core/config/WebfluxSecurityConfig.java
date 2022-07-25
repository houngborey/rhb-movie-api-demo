//package kh.dev.scott.movie.core.config;
//
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.Collections;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy;
//import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.CorsConfigurationSource;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//
//@Configuration
//@EnableWebFluxSecurity
//public class WebfluxSecurityConfig {
//
//	@Bean
//	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//		http.cors().configurationSource(corsConfigurationSource());
//		http.csrf().disable();
//		http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.mode(Mode.DENY)));
//		http.headers().xssProtection().disable();
//		http.authorizeExchange().anyExchange().permitAll();
//		http.httpBasic().disable();
//		http.headers(headers -> headers.referrerPolicy(
//				referrerPolicy -> referrerPolicy.policy(ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)));
//		http.headers(headers -> headers.contentTypeOptions(contentTypeOptions -> contentTypeOptions.disable()));
//
//		http.headers(headers -> headers
//				.hsts(hsts -> hsts.includeSubdomains(true).preload(true).maxAge(Duration.ofSeconds(63072000))));
//		return http.build();
//	}
//
//	@Bean
//	CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList("*"));
//		configuration.setAllowedMethods(Arrays.asList("POST"));
//		configuration.setAllowedHeaders(Collections.singletonList("*"));
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
//}
