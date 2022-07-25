package kh.dev.scott.movie.service;

import kh.dev.scott.movie.core.proto.RhbOutputMessage;
import kh.dev.scott.movie.model.MovieInput;
import reactor.core.publisher.Mono;

public interface IMovieService {
	public Mono<RhbOutputMessage> createMovie(MovieInput input);

	public Mono<RhbOutputMessage> getAllMovies();

	public Mono<RhbOutputMessage> findById(long id);

	public Mono<RhbOutputMessage> updateMovie(MovieInput input);

	public Mono<RhbOutputMessage> deleteMovie(long id);
}
