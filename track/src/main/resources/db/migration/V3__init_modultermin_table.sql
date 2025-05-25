create table modultermin(
    modul           integer references modul(id),
    termin_name     varchar(200) not null,
    start_date      timestamp(0) not null,
    ende_date       timestamp(0),
    notiz           varchar(200),
    terminfrequenz  varchar(20)
);