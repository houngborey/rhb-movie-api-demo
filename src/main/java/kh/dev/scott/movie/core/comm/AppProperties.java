package kh.dev.scott.movie.core.comm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@ConfigurationProperties
@Getter
public class AppProperties {

	@Value("${esb.r2dbc.driver}")
	private String esbR2dbcDriver;

	@Value("${esb.r2dbc.host}")
	private String esbR2dbcHost;

	@Value("${esb.r2dbc.post}")
	private int esbR2dbcPost;

	@Value("${esb.r2dbc.schema}")
	private String esbR2dbcSchema;

	@Value("${esb.r2dbc.username}")
	private String esbR2dbcUsername;

	@Value("${esb.r2dbc.password}")
	private String esbR2dbcPassword;

	@Value("${conn.pool.max.idle.time.minute}")
	private int connPoolMaxIdleTimeMinute;

	@Value("${conn.pool.max.life.time.minute}")
	private int connPoolMaxLifeTimeMinute;

	@Value("${conn.pool.time.out.minute}")
	private long connPoolTimeoutMinute;

	@Value("${conn.pool.max.size}")
	private int connPoolMaxSize;

	@Value("${conn.pool.init.size}")
	private int connPoolInitSize;

	@Value("${conn.pool.max.create.connection.time.second}")
	private int connPoolMaxCreateConnectionTimeSecond;

}
