package kh.dev.scott.movie.core.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;

import kh.dev.scott.movie.core.comm.CommFunc;
import kh.dev.scott.movie.core.comm.StepType;
import kh.dev.scott.movie.model.SystemTrace;
import kh.dev.scott.movie.repository.ISystemTraceReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class RequestLogger extends ServerHttpRequestDecorator {
	private ISystemTraceReactiveRepository loggerRepository;
	private String xRequestId;
	private CommFunc commFunc;

	public RequestLogger(ServerHttpRequest delegate, String xRequestId, ISystemTraceReactiveRepository loggerRepository,
			CommFunc commFunc) {
		super(delegate);
		this.xRequestId = xRequestId;
		this.loggerRepository = loggerRepository;
		this.commFunc = commFunc;
	}

	@Override
	public Flux<DataBuffer> getBody() {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		return super.getBody().doOnNext(dataBuffer -> {
			try {
				Channels.newChannel(baos).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
				String body = IOUtils.toString(baos.toByteArray(), "UTF-8");
				body = body.replace("\n", "").replace("\r", "").replaceAll("\\s", "");
				try {
					SystemTrace loggerEntity = new SystemTrace();
					loggerEntity.setRegister_datetime(commFunc.dateTimeNoSign());
					loggerEntity.setApi_request(body);
					loggerEntity.setProcess_id(xRequestId);
					loggerEntity.setEnd_point(String.valueOf(getDelegate().getPath()));
					loggerEntity.setStep_tp(StepType.REQUEST.toString());
					loggerRepository.save(loggerEntity).subscribe();

				} catch (Exception e) {
					log.error("error -> " + e.getMessage());
				}

			} catch (IOException e) {
				log.error("error -> " + e.getMessage());

			} finally {
				try {
					baos.close();
				} catch (IOException e) {
					log.error("error -> " + e.getMessage());

				}
			}
		});
	}

}
