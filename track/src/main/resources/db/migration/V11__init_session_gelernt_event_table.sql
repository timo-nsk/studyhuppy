create table session_beendet_event(
    id serial primary key,
    event_id uuid,
    username varchar(200),
    beendet_datum timestamp,
    abgebrochen bool
);

create table session_bewertung(
    session_beendet_event int references session_beendet_event(id) on delete cascade,
    konzentration_bewertung decimal check (konzentration_bewertung between 0 and 10),
    produktivitaet_bewertung decimal check (produktivitaet_bewertung between 0 and 10),
    schwierigkeit_bewertung decimal check (schwierigkeit_bewertung between 0 and 10)
);