package kh.dev.scott.movie.core.interceptor;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.classic.LoggerContext;
import kh.dev.scott.movie.core.comm.CommFunc;
import kh.dev.scott.movie.core.comm.UniqueIDGenerator;
import kh.dev.scott.movie.repository.ISystemTraceReactiveRepository;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;

@Configuration
@ConfigurationProperties(prefix = "logging.logstash")
public class ReactiveLoggerAutoConfig {
	private static final String LOGSTASH_APPENDER_NAME = "LOGSTASH";

	@Autowired
	private ISystemTraceReactiveRepository loggerRepository;

	@Autowired
	private CommFunc commFunc;

	@Value("${spring.application.name:-}")
	String name;

	@Bean
	public UniqueIDGenerator generator() {
		return new UniqueIDGenerator();
	}

	@Bean
	public ReactiveLoggerFilter reactiveSpringLoggingFilter() {
		return new ReactiveLoggerFilter(generator(), loggerRepository, commFunc);
	}

	@Bean
	@ConditionalOnProperty("logging.logstash.enabled")
	public LogstashTcpSocketAppender logstashAppender() {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		LogstashTcpSocketAppender logstashTcpSocketAppender = new LogstashTcpSocketAppender();
		logstashTcpSocketAppender.setName(LOGSTASH_APPENDER_NAME);
		logstashTcpSocketAppender.setContext(loggerContext);

		LogstashEncoder encoder = new LogstashEncoder();
		encoder.setContext(loggerContext);
		encoder.setIncludeContext(true);
		encoder.setCustomFields("{\"appname\":\"" + name + "\"}");
		encoder.start();
		logstashTcpSocketAppender.setEncoder(encoder);
		logstashTcpSocketAppender.start();
		return logstashTcpSocketAppender;
	}

}
