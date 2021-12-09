alter table politicians rename column average_rating to rating;
alter table politicians drop column if exists total_rating;
