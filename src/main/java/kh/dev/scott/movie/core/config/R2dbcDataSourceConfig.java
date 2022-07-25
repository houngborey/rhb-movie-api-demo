package kh.dev.scott.movie.core.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import kh.dev.scott.movie.core.comm.AppProperties;

@Configuration
@EnableTransactionManagement
@EnableR2dbcRepositories(basePackages = { "kh.dev.scott.movie.repository" }, entityOperationsRef = "esbEntityTemplate")
public class R2dbcDataSourceConfig extends AbstractR2dbcConfiguration {
	@Autowired
	private AppProperties properties;

	@Primary
	@Override
	@Bean
	@Qualifier("connectionFactory")
	public ConnectionFactory connectionFactory() {
		ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
				.option(ConnectionFactoryOptions.DRIVER, properties.getEsbR2dbcDriver())
				.option(ConnectionFactoryOptions.HOST, properties.getEsbR2dbcHost())
				.option(ConnectionFactoryOptions.PORT, properties.getEsbR2dbcPost())
				.option(ConnectionFactoryOptions.DATABASE, properties.getEsbR2dbcSchema())
				.option(ConnectionFactoryOptions.USER, properties.getEsbR2dbcUsername())
				.option(ConnectionFactoryOptions.PASSWORD, properties.getEsbR2dbcPassword())
				.option(ConnectionFactoryOptions.CONNECT_TIMEOUT,
						Duration.ofMinutes(properties.getConnPoolTimeoutMinute()))
//				.option(ConnectionFactoryOptions.STATEMENT_TIMEOUT,
//						Duration.ofMinutes(properties.getConnPoolTimeoutMinute()))
				.build();

		// Create a ConnectionPool for connectionFactory
		ConnectionPoolConfiguration configuration = ConnectionPoolConfiguration
				.builder(ConnectionFactories.get(options))
				.maxIdleTime(Duration.ofMinutes(properties.getConnPoolMaxIdleTimeMinute()))
				.maxLifeTime(Duration.ofMinutes(properties.getConnPoolMaxLifeTimeMinute()))
				.maxSize(properties.getConnPoolMaxSize()).initialSize(properties.getConnPoolInitSize())
				.maxCreateConnectionTime(Duration.ofSeconds(properties.getConnPoolMaxCreateConnectionTimeSecond()))
				.build();

		return new ConnectionPool(configuration);

	}

	@Bean
	public R2dbcEntityOperations esbEntityTemplate(
			@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
		return new R2dbcEntityTemplate(connectionFactory);
	}

}
