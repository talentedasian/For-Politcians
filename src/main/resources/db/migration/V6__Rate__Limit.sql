drop table if exists rate_limit_jpa_entity;

create table rate_limit_jpa_entity (
	id bigint PRIMARY KEY,
	accountNumber text NOT NULL,
	politicianNumber text NOT NULL,
	dateCreated DATE
);
