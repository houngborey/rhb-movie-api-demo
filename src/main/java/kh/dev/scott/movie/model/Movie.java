package kh.dev.scott.movie.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6568359700820813835L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String title;
	private String category;
	private String star;
	private String create_at;
	private String update_at;

}
