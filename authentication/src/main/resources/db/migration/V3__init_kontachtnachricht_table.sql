create table kontakt_nachricht(
    id serial primary key,
    nachricht_id uuid,
    betreff varchar(100),
    nachricht text,
    bearbeitung_status varchar(25)

)