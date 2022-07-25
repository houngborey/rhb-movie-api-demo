package kh.dev.scott.movie.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.protobuf.util.JsonFormat;

import kh.dev.scott.movie.core.comm.CommFunc;
import kh.dev.scott.movie.core.comm.Constant;
import kh.dev.scott.movie.core.comm.OutputStatus;
import kh.dev.scott.movie.core.proto.RhbOutputMessage;
import kh.dev.scott.movie.core.proto.RhbOutputMessage.Builder;
import kh.dev.scott.movie.core.proto.data.RhbOutputDataMessage;
import kh.dev.scott.movie.model.MovieInput;
import kh.dev.scott.movie.service.IMovieService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movie")
@Slf4j
public class MovieController {

	@Autowired
	private IMovieService service;

	@Autowired
	private CommFunc commFunc;

	/*
	 * function to create movie
	 */
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<RhbOutputMessage> doCreateMovie(@RequestBody MovieInput input) {
		Builder output = RhbOutputMessage.newBuilder();
		RhbOutputDataMessage.Builder data = RhbOutputDataMessage.newBuilder();
		data.setTimestamp(commFunc.timestamp()).setTraceNo(commFunc.traceNo());
		try {
			/*
			 * check category empty
			 */
			if (StringUtils.isBlank(input.getCategory())) {
				output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC406).setMessage(Constant.EM406)
						.setData(data);
				log.error("ERROR --> " + JsonFormat.printer().print(output.build()));
				return Mono.just(output.build());

			}

			/*
			 * check category length
			 */
			if (StringUtils.length(input.getCategory()) > 500) {
				output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC407).setMessage(Constant.EM407)
						.setData(data);
				log.error("ERROR --> " + JsonFormat.printer().print(output.build()));
				return Mono.just(output.build());

			}

			/*
			 * check star empty
			 */
			if (StringUtils.isBlank(input.getStar())) {
				output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC408).setMessage(Constant.EM408)
						.setData(data);
				log.error("ERROR --> " + JsonFormat.printer().print(output.build()));
				return Mono.just(output.build());

			}

			/*
			 * check star is numberic check star range input
			 */
			if (StringUtils.isNotBlank(input.getStar())) {
				if (!NumberUtils.isCreatable(input.getStar())) {
					output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC409).setMessage(Constant.EM409)
							.setData(data);
					log.error("ERROR --> " + JsonFormat.printer().print(output.build()));
					return Mono.just(output.build());
				}

				float fStar = NumberUtils.toFloat(input.getStar());
				if (fStar < 0.5 || fStar > 5) {
					output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC410).setMessage(Constant.EM410)
							.setData(data);
					log.error("ERROR --> " + JsonFormat.printer().print(output.build()));
					return Mono.just(output.build());
				}

			}

			/*
			 * check ttile empty
			 */
			if (StringUtils.isBlank(input.getTitle())) {
				output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC411).setMessage(Constant.EM411)
						.setData(data);
				log.error("ERROR --> " + JsonFormat.printer().print(output.build()));
				return Mono.just(output.build());

			}

			/*
			 * check title length
			 */
			if (StringUtils.length(input.getTitle()) > 500) {
				output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC412).setMessage(Constant.EM412)
						.setData(data);
				log.error("ERROR --> " + JsonFormat.printer().print(output.build()));
				return Mono.just(output.build());

			}

			/*
			 * output data
			 */
			return service.createMovie(input);

		} catch (Exception e) {
			output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC503).setMessage(Constant.EM503)
					.setData(data);
			log.error("ERROR --> " + e.getMessage());
			return Mono.just(output.build());
		}

	}

	/*
	 * function to find movie by id
	 */
	@PostMapping(value = "/findById", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<RhbOutputMessage> doFindById(@RequestBody MovieInput input) {
		Builder output = RhbOutputMessage.newBuilder();
		RhbOutputDataMessage.Builder data = RhbOutputDataMessage.newBuilder();
		data.setTimestamp(commFunc.timestamp()).setTraceNo(commFunc.traceNo());

		try {
			return service.findById(input.getId());

		} catch (Exception e) {
			output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC503).setMessage(Constant.EM503)
					.setData(data);
			log.error("ERROR --> " + e.getMessage());
			return Mono.just(output.build());
		}

	}

	/*
	 * function get all movies
	 */
	@PostMapping(value = "/getAllMovies", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<RhbOutputMessage> doGetAllMovies() {
		Builder output = RhbOutputMessage.newBuilder();
		RhbOutputDataMessage.Builder data = RhbOutputDataMessage.newBuilder();
		data.setTimestamp(commFunc.timestamp()).setTraceNo(commFunc.traceNo());
		try {
			return service.getAllMovies();

		} catch (Exception e) {
			output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC503).setMessage(Constant.EM503)
					.setData(data);
			log.error("ERROR --> " + e.getMessage());
			return Mono.just(output.build());
		}

	}

	/*
	 * function to delete movie by id
	 */
	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<RhbOutputMessage> doUpdateMovie(@RequestBody MovieInput input) {
		Builder output = RhbOutputMessage.newBuilder();
		RhbOutputDataMessage.Builder data = RhbOutputDataMessage.newBuilder();
		data.setTimestamp(commFunc.timestamp()).setTraceNo(commFunc.traceNo());

		try {
			/*
			 * check category empty
			 */
			if (StringUtils.isBlank(input.getCategory())) {
				output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC406).setMessage(Constant.EM406)
						.setData(data);
				log.error("ERROR --> " + JsonFormat.printer().print(output.build()));
				return Mono.just(output.build());

			}

			/*
			 * check category length
			 */
			if (StringUtils.length(input.getCategory()) > 500) {
				output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC407).setMessage(Constant.EM407)
						.setData(data);
				log.error("ERROR --> " + JsonFormat.printer().print(output.build()));
				return Mono.just(output.build());

			}

			/*
			 * check star empty
			 */
			if (StringUtils.isBlank(input.getStar())) {
				output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC408).setMessage(Constant.EM408)
						.setData(data);
				log.error("ERROR --> " + JsonFormat.printer().print(output.build()));
				return Mono.just(output.build());

			}

			/*
			 * check star is numberic check star range input
			 */
			if (StringUtils.isNotBlank(input.getStar())) {
				if (!NumberUtils.isCreatable(input.getStar())) {
					output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC409).setMessage(Constant.EM409)
							.setData(data);
					log.error("ERROR --> " + JsonFormat.printer().print(output.build()));
					return Mono.just(output.build());
				}

				float fStar = NumberUtils.toFloat(input.getStar());
				if (fStar < 0.5 || fStar > 5) {
					output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC410).setMessage(Constant.EM410)
							.setData(data);
					log.error("ERROR --> " + JsonFormat.printer().print(output.build()));
					return Mono.just(output.build());
				}

			}

			/*
			 * check ttile empty
			 */
			if (StringUtils.isBlank(input.getTitle())) {
				output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC411).setMessage(Constant.EM411)
						.setData(data);
				log.error("ERROR --> " + JsonFormat.printer().print(output.build()));
				return Mono.just(output.build());

			}

			/*
			 * check title length
			 */
			if (StringUtils.length(input.getTitle()) > 500) {
				output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC412).setMessage(Constant.EM412)
						.setData(data);
				log.error("ERROR --> " + JsonFormat.printer().print(output.build()));
				return Mono.just(output.build());

			}
			return service.updateMovie(input);

		} catch (Exception e) {
			output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC503).setMessage(Constant.EM503)
					.setData(data);
			log.error("ERROR --> " + e.getMessage());
			return Mono.just(output.build());
		}

	}

	/*
	 * function update movie by id
	 */
	@PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<RhbOutputMessage> doDeleteMovie(@RequestBody MovieInput input) {
		Builder output = RhbOutputMessage.newBuilder();
		RhbOutputDataMessage.Builder data = RhbOutputDataMessage.newBuilder();
		data.setTimestamp(commFunc.timestamp()).setTraceNo(commFunc.traceNo());
		try {
			return service.deleteMovie(input.getId());

		} catch (Exception e) {
			output.setStatus(OutputStatus.error.toString()).setCode(Constant.EC503).setMessage(Constant.EM503)
					.setData(data);
			log.error("ERROR --> " + e.getMessage());
			return Mono.just(output.build());
		}

	}

}
