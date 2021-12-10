drop table if exists politicians cascade;
drop table if exists rating cascade;
drop table if exists rate_limit_jpa_entity cascade;

create table politicians (
	id text PRIMARY KEY,
	first_name text NOT NULL,
	last_name text,
	full_name text NOT NULL,
	total_rating numeric,
	average_rating numeric(4,3) CONSTRAINT average_rating_maximum CHECK (average_rating < 10),
	total_count_of_rating integer NOT NULL,
	dtype varchar(50) NOT NULL,
	law_signed text,
	months_of_service integer,
	law_made text
);

create table rating (
	id integer PRIMARY KEY,
	rating numeric(4,3) NOT NULL,
	name text NOT NULL,
	party text NOT NULL,
	email text NOT NULL,
	account_number text NOT NULL,
	politician_id text references politicians(id)
);

create table rate_limit_jpa_entity (
	id bigint PRIMARY KEY,
	account_number text NOT NULL,
	politician_number text NOT NULL,
	date_created date NOT NULL
);

create unique index if not exists id on rating (id);
create index if not exists rating_id on rating (politician_id);
create unique index if not exists politicians on politicians (id);
