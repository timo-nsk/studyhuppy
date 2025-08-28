create table lernplan(
    id serial primary key,
    fach_id uuid not null,
    username varchar(200) not null,
    titel varchar(100),
    is_active boolean not null
);

create table tag(
    tag varchar(9),
    beginn time,
    session_id uuid,
    lernplan int references lernplan(id) on delete cascade,
    lernplan_key int
);