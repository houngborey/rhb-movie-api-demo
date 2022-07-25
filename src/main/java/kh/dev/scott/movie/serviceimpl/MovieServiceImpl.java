package kh.dev.scott.movie.serviceimpl;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.dev.scott.movie.core.comm.CommFunc;
import kh.dev.scott.movie.core.comm.Constant;
import kh.dev.scott.movie.core.comm.OutputStatus;
import kh.dev.scott.movie.core.proto.RhbOutputMessage;
import kh.dev.scott.movie.core.proto.data.RhbOutputDataMessage;
import kh.dev.scott.movie.core.proto.data.item.RhbOutputDataItemMessage;
import kh.dev.scott.movie.model.Movie;
import kh.dev.scott.movie.model.MovieInput;
import kh.dev.scott.movie.repository.IMovieReactiveRepository;
import kh.dev.scott.movie.service.IMovieService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class MovieServiceImpl implements IMovieService {

	@Autowired
	private CommFunc commFunc;

	@Autowired
	private IMovieReactiveRepository repository;

	/*
	 * function for create movie
	 */
	@Override
	public Mono<RhbOutputMessage> createMovie(MovieInput input) {
		final RhbOutputMessage.Builder output = RhbOutputMessage.newBuilder();
		final RhbOutputDataMessage.Builder data = RhbOutputDataMessage.newBuilder();
		data.setTimestamp(commFunc.timestamp()).setTraceNo(commFunc.traceNo());
		try {
			Movie movie = new Movie();
			movie.setCategory(input.getCategory());
			movie.setStar(input.getStar());
			movie.setTitle(input.getTitle());
			movie.setCreate_at(commFunc.dateTimeNoSign());

			return repository.save(movie).flatMap(new Function<Movie, Mono<? extends RhbOutputMessage>>() {
				@Override
				public Mono<? extends RhbOutputMessage> apply(Movie c) {
					data.setId(c.getId()).setCategory(c.getCategory()).setStar(c.getStar()).setTitle(c.getTitle());
					output.setCode(Constant.SC000).setStatus(OutputStatus.ok.toString()).setMessage(Constant.SM000)
							.setData(data);
					return Mono.just(output.build());
				}
			});

		} catch (Exception e) {
			log.error("error createMovie -> " + e.getMessage());
			output.setCode(Constant.EC503).setStatus(OutputStatus.error.toString()).setMessage(Constant.EM503)
					.setData(data);
			return Mono.just(output.build());

		}
	}

	@Override
	public Mono<RhbOutputMessage> getAllMovies() {
		RhbOutputMessage.Builder output = RhbOutputMessage.newBuilder();
		RhbOutputDataMessage.Builder data = RhbOutputDataMessage.newBuilder();
		data.setTimestamp(commFunc.timestamp()).setTraceNo(commFunc.traceNo());
		try {
			return repository.findAll().collectList().flatMap(list -> {
				if (list.size() <= 0) {
					output.setCode(Constant.EC404).setStatus(OutputStatus.error.toString()).setMessage(Constant.EM404)
							.setData(data);
					return Mono.just(output.build());
				}

				list.stream().forEach(e -> {
					RhbOutputDataItemMessage item = RhbOutputDataItemMessage.newBuilder().setCategory(e.getCategory())
							.setStar(e.getStar()).setTitle(e.getTitle()).setId(e.getId()).build();
					data.addItems(item);

				});

				output.setCode(Constant.SC000).setStatus(OutputStatus.ok.toString()).setMessage(Constant.SM000)
						.setData(data);
				return Mono.just(output.build());
			});

		} catch (Exception e) {
			log.error("error getAllMovies -> " + e.getMessage());
			output.setCode(Constant.EC503).setStatus(OutputStatus.error.toString()).setMessage(Constant.EM503)
					.setData(data);
			return Mono.just(output.build());

		}
	}

	@Override
	public Mono<RhbOutputMessage> findById(long id) {
		RhbOutputMessage.Builder output = RhbOutputMessage.newBuilder();
		RhbOutputDataMessage.Builder data = RhbOutputDataMessage.newBuilder();
		data.setTimestamp(commFunc.timestamp()).setTraceNo(commFunc.traceNo());
		try {
			return repository.findById(id).flatMap(c -> {
				data.setCategory(c.getCategory()).setId(id).setStar(c.getStar()).setTitle(c.getTitle());
				output.setCode(Constant.SC000).setStatus(OutputStatus.ok.toString()).setMessage(Constant.SM000)
						.setData(data);
				return Mono.just(output.build());

			}).switchIfEmpty(Mono.just(output.setData(data).setStatus(OutputStatus.error.toString())
					.setCode(Constant.EC413).setMessage(Constant.EM413).build()));

		} catch (Exception e) {
			log.error("error findById -> " + e.getMessage());
			output.setCode(Constant.EC503).setStatus(OutputStatus.error.toString()).setMessage(Constant.EC503)
					.setData(data);
			return Mono.just(output.build());

		}
	}

	@Override
	public Mono<RhbOutputMessage> updateMovie(MovieInput input) {
		RhbOutputMessage.Builder output = RhbOutputMessage.newBuilder();
		RhbOutputDataMessage.Builder data = RhbOutputDataMessage.newBuilder();
		data.setTimestamp(commFunc.timestamp()).setTraceNo(commFunc.traceNo());
		try {
			Movie movie = new Movie();
			movie.setCategory(input.getCategory());
			movie.setStar(input.getStar());
			movie.setTitle(input.getTitle());
			movie.setId(input.getId());
			movie.setUpdate_at(commFunc.dateTimeNoSign());

			return repository.findById(input.getId()).flatMap(f -> repository.save(movie).then(Mono.just(f)))
					.flatMap(u -> {
						data.setId(input.getId()).setCategory(input.getCategory()).setTimestamp(input.getTitle())
								.setStar(input.getStar());
						output.setCode("000").setStatus(OutputStatus.ok.toString()).setMessage("success").setData(data);
						return Mono.just(output.build());

					}).switchIfEmpty(Mono.just(output.setData(data).setStatus(OutputStatus.error.toString())
							.setCode(Constant.EC413).setMessage(Constant.EM413).build()));

		} catch (Exception e) {
			log.error("error updateMovie -> " + e.getMessage());
			output.setCode(Constant.EC503).setStatus(OutputStatus.error.toString()).setMessage(Constant.EC503)
					.setData(data);
			return Mono.just(output.build());
		}

	}

	@Override
	public Mono<RhbOutputMessage> deleteMovie(long id) {
		RhbOutputMessage.Builder output = RhbOutputMessage.newBuilder();
		RhbOutputDataMessage.Builder data = RhbOutputDataMessage.newBuilder();
		data.setTimestamp(commFunc.timestamp()).setTraceNo(commFunc.traceNo());
		try {
			return repository.findById(id).flatMap(f -> repository.deleteById(id).then(Mono.just(f))).flatMap(u -> {
				data.setId(id);
				output.setCode("000").setStatus(OutputStatus.ok.toString()).setMessage("success").setData(data);
				return Mono.just(output.build());

			}).switchIfEmpty(Mono.just(output.setData(data).setStatus(OutputStatus.error.toString())
					.setCode(Constant.EC413).setMessage(Constant.EM413).build()));

		} catch (Exception e) {
			log.error("error deleteMovie -> " + e.getMessage());
			output.setCode(Constant.EC503).setStatus(OutputStatus.error.toString()).setMessage(Constant.EC503)
					.setData(data);
			return Mono.just(output.build());
		}

	}

}
