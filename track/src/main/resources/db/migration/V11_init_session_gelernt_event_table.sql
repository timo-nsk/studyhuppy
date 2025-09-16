create table session_beendet_event(
    event_id uuid,
    username varchar(200),
    beendet_datum timestamp,
    abgebrochen bool
);

create table session_bewertung(
    session_beendet_event int references session_beendet_event(event_id),
    konzentration_bewertung decimal,
    produktivitaet_bewertung decimal,
    schwierigkeit_bewertung decimal
);