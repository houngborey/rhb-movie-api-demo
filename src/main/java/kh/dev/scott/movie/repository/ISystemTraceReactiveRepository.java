package kh.dev.scott.movie.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import kh.dev.scott.movie.model.SystemTrace;

public interface ISystemTraceReactiveRepository extends ReactiveCrudRepository<SystemTrace, String> {

}
