create table modul
(
    id serial primary key,
    fach_id uuid,
    name varchar(100),
    seconds_learned int,
    credit_points int,
    username varchar(200),
    active boolean
);

create table statistic (
    id serial primary key,
    date date,
    fach_id uuid,
    modul int references modul(id) ON DELETE CASCADE,
    modul_key int
);

create table study_interval (
    start timestamp not null ,
    finish timestamp not null,
    modul_name varchar(100),
    statistic int references statistic(id) ON DELETE CASCADE,
    statistic_key int
);