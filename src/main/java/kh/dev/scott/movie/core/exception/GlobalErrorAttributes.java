package kh.dev.scott.movie.core.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;
import com.googlecode.protobuf.format.JsonFormat;

import kh.dev.scott.movie.core.comm.CommFunc;
import kh.dev.scott.movie.core.comm.OutputStatus;
import kh.dev.scott.movie.core.proto.RhbOutputMessage;
import kh.dev.scott.movie.core.proto.RhbOutputMessage.Builder;
import kh.dev.scott.movie.core.proto.data.RhbOutputDataMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalErrorAttributes extends DefaultErrorAttributes {

	@Autowired
	private CommFunc commFunc;

	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();

		Builder output = RhbOutputMessage.newBuilder();
		RhbOutputDataMessage.Builder data = RhbOutputDataMessage.newBuilder();
		data.setTimestamp(commFunc.timestamp()).setTraceNo(commFunc.traceNo());

		try {
			Throwable throwable = getError(request);

			ResponseStatusException exception = (ResponseStatusException) throwable;
			HttpStatus httpStatus = exception.getStatus();
			String result = httpStatus.toString();
			String[] error = result.split(" ");

			output.setCode(error[0]).setMessage(error[1]).setStatus(OutputStatus.error.toString()).setData(data);

			JsonFormat jsonFormat = new JsonFormat();
			String json = jsonFormat.printToString(output.build());
			Gson gson = new Gson();
			map = gson.fromJson(json, LinkedHashMap.class);
		} catch (Exception e) {
			log.error("error Global Handler -> " + e.getMessage());
			output.setCode("503").setMessage("503").setStatus(OutputStatus.error.toString()).setData(data);

			JsonFormat jsonFormat = new JsonFormat();
			String json = jsonFormat.printToString(output.build());
			Gson gson = new Gson();
			map = gson.fromJson(json, LinkedHashMap.class);
		}

		return map;
	}
}