package kh.dev.scott.movie.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class MovieInput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7172156360341573023L;
	private String title;
	private String category;
	private String star;
	private long id;

}
