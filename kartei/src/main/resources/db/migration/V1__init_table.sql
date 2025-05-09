create table stapel(
    id serial primary key,
    fach_id uuid,
    modul_fach_id uuid,
    name varchar(255) not null,
    beschreibung text,
    lern_intervalle varchar(100),
    username varchar(200)
);

create table karteikarte(
    id serial primary key,
    fach_id uuid,
    frage text,
    antwort text,
    erstellt_am timestamp not null,
    letzte_aenderung_am timestamp not null,
    faellig_am timestamp not null,
    notiz text,
    was_hard int,
    frage_typ varchar(20),
    antwortzeit_sekunden int,
    lernstufen varchar(200),
    stapel int references stapel(id),
    stapel_key int
);

create table antwort(
    antwort text,
    wahrheit boolean,
    karteikarte int references karteikarte(id),
    karteikarte_key int,
    stapel int references stapel(id),
    stapel_key int
);


