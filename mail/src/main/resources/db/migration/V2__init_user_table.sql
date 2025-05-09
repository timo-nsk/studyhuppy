create table "user"(
    id serial primary key,
    user_id uuid,
    username varchar(200) not null unique,
    password varchar(200) not null
);