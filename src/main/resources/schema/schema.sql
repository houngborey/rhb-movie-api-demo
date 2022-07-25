create table movie(
	id int NOT NULL AUTO_INCREMENT, 
	title varchar(500) NOT NULL,
	category varchar(500) NOT NULL,
	star varchar(5), 
	create_at varchar(30), 
	update_at varchar(30), 
	 PRIMARY KEY (id)
); 

CREATE INDEX index_movie ON movie (id, title, category);


create table system_trace (
register_datetime varchar(50),
trace_no varchar(50),
process_id varchar(50),
code varchar(10),
message varchar(1000),
api_request varchar(4000),
api_response varchar(4000),
time_elapsed varchar(20),
end_point varchar(500),
step_tp varchar(100), 
request_time varchar(100), 
response_time varchar(100)
)

create index index_system_trace on
system_trace (register_datetime,
trace_no,
process_id,
step_tp);