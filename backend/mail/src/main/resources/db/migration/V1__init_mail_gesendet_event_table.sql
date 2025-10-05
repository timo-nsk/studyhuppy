create table mail_gesendet_event(
    id serial primary key,
    user_id uuid,
    gesendet_am timestamp,
    typ varchar(30),
    erfolgreich_versendet boolean
);