create table karteikarte_gelernt_event(
    id serial primary key,
    stapel_id uuid,
    karteikarte_id uuid,
    gelernt_am timestamp,
    seconds_needed int
);