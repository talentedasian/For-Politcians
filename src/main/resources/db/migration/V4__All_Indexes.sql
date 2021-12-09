create unique index if not exists id on rating (id);
create index if not exists rating_id on rating (politician_id);
create unique index if not exists politicians on politicians (id);

