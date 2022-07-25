package kh.dev.scott.movie.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class SystemTrace implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6636261818109550285L;

	private String register_datetime;
	private String trace_no;
	private String process_id;
	private String code;
	private String message;
	private String api_request;
	private String api_response;
	private String time_elapsed;
	private String request_time;
	private String response_time;
	private String end_point;
	private String step_tp;

}
