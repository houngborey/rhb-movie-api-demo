package kh.dev.scott.movie.core.comm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommFunc {
	CommFunc() {

	}

	public String traceNo() {
		try {
			NoArgGenerator timeBasedGenerator = Generators.timeBasedGenerator();
			UUID uuid = timeBasedGenerator.generate();
			return uuid.toString();
		} catch (Exception e) {
			log.error("error -> " + e.getMessage());
		}
		return "";

	}

	public String timestamp() {
		try {
			return LocalDateTime.now().toString();
		} catch (Exception e) {
			log.error("error -> " + e.getMessage());
		}
		return "";

	}

	public String dateTimeNoSign() {
		try {
			DateTimeFormatter fmDataTime = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
			return LocalDateTime.now().format(fmDataTime);
		} catch (Exception e) {
			log.error("error -> " + e.getMessage());
		}
		return "";

	}
}
