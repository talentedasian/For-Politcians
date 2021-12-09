create table politicians (
	id text PRIMARY KEY,
	first_name text NOT NULL,
	last_name text,
	full_name text NOT NULL,
	total_rating numeric(3,3),
	average_rating(3,4),
	total_count_of_rating integer NOT NULL
);

create table rating (
	id integer PRIMARY KEY,
	rating numeric(4,3) NOT NULL,
	name text NOT NULL,
	party text NOT NULL,
	email text NOT NULL,
	account_number text NOT NULL,
	politician_id text references politicians(id))
);


