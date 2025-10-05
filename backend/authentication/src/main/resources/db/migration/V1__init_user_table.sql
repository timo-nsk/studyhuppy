create table users (
    id serial primary key,
    user_id uuid,
    mail varchar(100),
    username varchar(200) not null unique,
    password varchar(200) not null,
    notification_subscription boolean,
    accepted_agb boolean not null,
    semester int
);