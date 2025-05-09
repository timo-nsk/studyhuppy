create table modul
(
    id serial primary key,
    fach_id uuid,
    name varchar(100),
    seconds_learned int,
    username varchar(200),
    active boolean,
    semesterstufe int,
    klausur_date timestamp
);

create table modul_gelernt_event
(
    id serial primary key,
    event_id uuid not null,
    modul_id uuid not null,
    username varchar(200) not null,
    seconds_learned int not null,
    date_gelernt date not null
);

create table semester(
    modul int PRIMARY KEY REFERENCES modul(id) ON DELETE CASCADE,
    semester_typ varchar(20),
    vorlesung_beginn date,
    vorlesung_ende date,
    semester_beginn date,
    semester_ende date
);

create table kreditpunkte(
    modul int PRIMARY KEY REFERENCES modul(id) ON DELETE CASCADE,
    anzahl_punkte int,
    kontaktzeit_stunden int,
    selbststudium_stunden int
);

create table lerntage(
    modul int PRIMARY KEY REFERENCES modul(id) ON DELETE CASCADE,
    mondays boolean,
    tuesdays boolean,
    wednesdays boolean,
    thursdays boolean,
    fridays boolean,
    saturdays boolean,
    sundays boolean,
    semester_phase varchar(10)
);