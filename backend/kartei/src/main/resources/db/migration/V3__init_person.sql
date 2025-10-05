create table person(
    id serial primary key
);

create table pet(
    id serial primary key,
    person int references person(id),
    person_key int
);

create table futter(
    futter_name varchar(100),
    pet int references pet(id),
    pet_key int
);