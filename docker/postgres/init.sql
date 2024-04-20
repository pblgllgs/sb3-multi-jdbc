CREATE TABLE logs(
    id serial primary key,
    date timestamp not null,
    message TEXT not null,
    tier varchar(10) not null
);