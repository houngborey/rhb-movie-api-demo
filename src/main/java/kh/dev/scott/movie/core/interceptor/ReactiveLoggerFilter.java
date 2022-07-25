package kh.dev.scott.movie.core.interceptor;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import kh.dev.scott.movie.core.comm.CommFunc;
import kh.dev.scott.movie.core.comm.UniqueIDGenerator;
import kh.dev.scott.movie.repository.ISystemTraceReactiveRepository;
import reactor.core.publisher.Mono;

public class ReactiveLoggerFilter implements WebFilter {
	private UniqueIDGenerator generator;
	private ISystemTraceReactiveRepository loggerRepository;
	private CommFunc commFunc;

	public ReactiveLoggerFilter(UniqueIDGenerator generator, ISystemTraceReactiveRepository loggerRepository,
			CommFunc commFunc) {
		this.generator = generator;
		this.loggerRepository = loggerRepository;
		this.commFunc = commFunc;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		final String xRequestId = commFunc.traceNo();
		generator.generateAndSetMDC(exchange.getRequest());
		final long startTime = System.currentTimeMillis();
		final String endpoint = exchange.getRequest().getPath().toString();
		ServerWebExchangeDecorator exchangeDecorator = new ServerWebExchangeDecorator(exchange) {
			@Override
			public ServerHttpRequest getRequest() {
				return new RequestLogger(super.getRequest(), xRequestId, loggerRepository, commFunc);
			}

			@Override
			public ServerHttpResponse getResponse() {
				return new ResponseLogger(super.getResponse(), startTime, xRequestId, endpoint, loggerRepository,
						commFunc);
			}
		};
		return chain.filter(exchangeDecorator);
	}

}