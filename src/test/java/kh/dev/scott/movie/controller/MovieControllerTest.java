package kh.dev.scott.movie.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import kh.dev.scott.movie.core.comm.CommFunc;
import kh.dev.scott.movie.model.Movie;
import kh.dev.scott.movie.repository.IMovieReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class MovieControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private IMovieReactiveRepository repository;

	@Autowired
	private DatabaseClient databaseClient;

	@Autowired
	private CommFunc commFunc;

	private List<Movie> initData() {
		return Arrays.asList(new Movie(0L, "Action", "The Northman (English)", "3.4", commFunc.dateTimeNoSign(), null),
				new Movie(0L, "Action", "Dark World", "2.5", commFunc.dateTimeNoSign(), null),
				new Movie(0L, "Comedy", "Single Dad", "4.3", commFunc.dateTimeNoSign(), null),
				new Movie(0L, "Ghost", "The Invitation", "3.4", commFunc.dateTimeNoSign(), null),
				new Movie(0L, "Animation", "DC League of Super-Pets", "5", commFunc.dateTimeNoSign(), null));
	}

	@BeforeEach
	public void setup() {
		List<String> statements = Arrays.asList("DROP TABLE IF EXISTS movie ;",
				"CREATE TABLE `movie` (\r\n" + "  `id` int(11) NOT NULL AUTO_INCREMENT,\r\n"
						+ "  `title` varchar(500) NOT NULL,\r\n" + "  `category` varchar(500) NOT NULL,\r\n"
						+ "  `star` varchar(5) DEFAULT NULL,\r\n" + "  `create_at` varchar(30) DEFAULT NULL,\r\n"
						+ "  `update_at` varchar(30) DEFAULT NULL,\r\n" + "  PRIMARY KEY (`id`),\r\n"
						+ "  KEY `index_movie` (`id`,`title`,`category`)\r\n" + ")");

		statements.forEach(it -> databaseClient.sql(it).fetch().rowsUpdated().block());

		repository.deleteAll().thenMany(Flux.fromIterable(initData())).flatMap(repository::save).doOnNext(movie -> {

		}).blockLast();

	}

	@Test
	@Order(1)
	public void doGetAllMovies() {
		webTestClient.post().uri("/movie/getAllMovies").exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_JSON_VALUE).expectBodyList(Movie.class);
	}

	@Test
	@Order(2)
	public void doCreateMovie() {
		Movie movie = new Movie(0L, "Action", "The Northman (English)", "3.4", commFunc.dateTimeNoSign(), null);
		webTestClient.post().uri("/movie/create").contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
				.body(Mono.just(movie), Movie.class).exchange().expectStatus().isOk();
	}

	@Test
	@Order(3)
	public void doFindById() {
		Movie movie = new Movie();
		movie.setId(1);
		webTestClient.post().uri("/movie/findById").contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
				.body(Mono.just(movie), Movie.class).exchange().expectStatus().isOk().expectBody()
				.jsonPath("$.status", "ok");
		;
	}

	@Test
	@Order(4)
	public void doUpdateMovie() {
		String title = "The Northman (Khmer-Korea)";
		Movie movie = new Movie();
		movie.setId(2);
		movie.setCategory("Action");
		movie.setStar("4.0");
		movie.setTitle(title);
		movie.setUpdate_at(commFunc.dateTimeNoSign());

		webTestClient.post().uri("/movie/update").contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
				.accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)).body(Mono.just(movie), Movie.class)
				.exchange().expectStatus().isOk();
	}

	@Test
	@Order(5)
	public void doDeleteMovie() {
		Movie movie = new Movie();
		movie.setId(2);
		webTestClient.post().uri("/movie/delete").contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
				.accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)).body(Mono.just(movie), Movie.class)
				.exchange().expectStatus().isOk();
	}

	@Test
	@Order(6)
	public void doFindById_NOT_FOUND() {
		Movie movie = new Movie();
		movie.setId(100);
		webTestClient.post().uri("/movie/findById").contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
				.body(Mono.just(movie), Movie.class).exchange().expectStatus().isNotFound().expectBody();
	}

	@Test
	@Order(2)
	public void doCreateMovie_IS_FAILD() {
		Movie movie = new Movie(0L, "", "The Northman (English)", "3.4", commFunc.dateTimeNoSign(), null);
		webTestClient.post().uri("/movie/create").contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
				.body(Mono.just(movie), Movie.class).exchange().expectStatus().is5xxServerError();
	}

}
