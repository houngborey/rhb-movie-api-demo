package kh.dev.scott.movie.core.interceptor;

import static net.logstash.logback.argument.StructuredArguments.value;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;

import org.apache.commons.io.IOUtils;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;

import com.google.gson.Gson;

import kh.dev.scott.movie.core.comm.CommFunc;
import kh.dev.scott.movie.core.comm.StepType;
import kh.dev.scott.movie.core.proto.RhbOutputMessage;
import kh.dev.scott.movie.core.proto.data.RhbOutputDataMessage;
import kh.dev.scott.movie.model.SystemTrace;
import kh.dev.scott.movie.repository.ISystemTraceReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class ResponseLogger extends ServerHttpResponseDecorator {
	private ISystemTraceReactiveRepository loggerRepository;
	private long startTime;
	private String xRequestId;
	private String endpoint;
	private CommFunc commFunc;

	public ResponseLogger(ServerHttpResponse delegate, long startTime, String xRequestId, String endpoint,
			ISystemTraceReactiveRepository loggerRepository, CommFunc commFunc) {
		super(delegate);
		this.startTime = startTime;
		this.xRequestId = xRequestId;
		this.loggerRepository = loggerRepository;
		this.endpoint = endpoint;
		this.commFunc = commFunc;	
	}

	@Override
	public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
		Flux<DataBuffer> buffer = Flux.from(body);
		return super.writeWith(buffer.doOnNext(dataBuffer -> {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				Channels.newChannel(baos).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
				String bodyRes = IOUtils.toString(baos.toByteArray(), "UTF-8");
				try {
					Gson gson = new Gson();
					RhbOutputMessage responseMessage = gson.fromJson(bodyRes, RhbOutputMessage.class);
					RhbOutputDataMessage data = responseMessage.getData();

					SystemTrace loggerEntity = new SystemTrace();
					loggerEntity.setRegister_datetime(commFunc.dateTimeNoSign());
					loggerEntity.setApi_response(bodyRes);
					loggerEntity.setProcess_id(xRequestId);
					loggerEntity.setTime_elapsed(
							String.valueOf(value("X-Response-Time", System.currentTimeMillis() - startTime)));
					loggerEntity.setCode(responseMessage.getCode());
					loggerEntity.setMessage(responseMessage.getMessage());
					loggerEntity.setTrace_no(data.getTraceNo());
					loggerEntity.setStep_tp(StepType.RESPONSE.toString());
					loggerEntity.setEnd_point(endpoint);
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
		}));
	}
}