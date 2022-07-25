package kh.dev.scott.movie.core.comm;

import java.util.List;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.http.server.reactive.ServerHttpRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UniqueIDGenerator {

	private static final String REQUEST_ID_HEADER_NAME = "X-Request-ID";
	private static final String CORRELATION_ID_HEADER_NAME = "X-Correlation-ID";

	public void generateAndSetMDC(ServerHttpRequest request) {
		try {
			MDC.clear();

			List<String> requestIds = request.getHeaders().get(REQUEST_ID_HEADER_NAME);
			if (requestIds == null)
				MDC.put(REQUEST_ID_HEADER_NAME, UUID.randomUUID().toString());
			else
				MDC.put(REQUEST_ID_HEADER_NAME, requestIds.get(0));

			List<String> correlationIds = request.getHeaders().get(CORRELATION_ID_HEADER_NAME);
			if (correlationIds == null)
				MDC.put(CORRELATION_ID_HEADER_NAME, UUID.randomUUID().toString());
			else
				MDC.put(CORRELATION_ID_HEADER_NAME, correlationIds.get(0));
		} catch (Exception e) {
			log.error("error -> " + e.getMessage());
		}

	}

}