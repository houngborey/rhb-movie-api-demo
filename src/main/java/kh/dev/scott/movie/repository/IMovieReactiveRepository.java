package kh.dev.scott.movie.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import kh.dev.scott.movie.model.Movie;

public interface IMovieReactiveRepository extends ReactiveCrudRepository<Movie, Long> {

	// @Query("select * from movie where 1=1 and id = :id")
	// public Mono<Movie> findById(String id);

	// @Query("delete from movie where 1=1 and id = :id")
	// public Mono<Movie> deleteMovie(String id);

//	@Query("update movie set title=:#{#movie.title}, category=:#{#movie.category} , star=:#{#movie.star}, update_at=:#{#movie.update_at} where 1=1 and id =:#{#movie.id}")
//	public Mono<Movie> updateMovie(Movie movie);

}
