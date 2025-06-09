INSERT INTO modul (id, fach_id, name, seconds_learned, username, active, semesterstufe) VALUES
    (1654654, 'f47ac10b-58cc-4372-a567-0e02b2c3d479','mod1', 10, 'user123', true, 1),
    (2546546, 'f47ac10b-58cc-4372-a567-0e12b2c3d479', 'mod2', 10,'user123', false, 1),
    (3846846, 'f47ac10c-58cc-4372-a537-0e02b2c3d479', 'mod3', 20,'peter4', false, 1),
    (4846846, 'f48ac10c-58cc-4372-a537-0e02b2c3d479', 'mod5', 30,'peter4', false, 1),
    (5846846, 'f49ac10c-58cc-4372-a537-0e02b2c3d479', 'mod4', 30,'peter4', false, 1),
    (6846846, 'f49bc10c-58cc-4372-a537-0e02b2c3d479', 'mod6', 10,'peter4', false, 1),
    (7846846, 'b8f6e2f5-91a0-4e6d-91b0-ff4e6932a82a', 'mod7', 10,'peter5', false, 1);
;

INSERT INTO semester (modul, semester_typ,  vorlesung_beginn, vorlesung_ende, semester_beginn, semester_ende) VALUES
    (1654654, 'WINTERSEMESTER', '2024-10-01', '2025-01-31', '2024-10-01', '2025-04-30'),
    (2546546, 'WINTERSEMESTER', '2024-10-01', '2025-01-31', '2024-10-01', '2025-04-30'),
    (3846846, 'WINTERSEMESTER', '2024-10-01', '2025-01-31', '2024-10-01', '2025-04-30');

INSERT INTO kreditpunkte (modul, anzahl_punkte, kontaktzeit_stunden, selbststudium_stunden) VALUES
    (1654654, 10, 90, 210),
    (2546546, 10, 90, 210),
    (3846846, 5, 60, 90),
    (4846846, 5, 60, 90);

INSERT INTO lerntage (modul, mondays, tuesdays, wednesdays, thursdays, fridays, saturdays, sundays, semester_phase) VALUES
    (1654654, true, false, true, false, true, false, false, 'VORLESUNG'),
    (2546546, true, false, true, false, true, false, false, 'VORLESUNG'),
    (3846846, true, false, true, false, true, false, false, 'VORLESUNG'),
    (4846846, true, false, true, false, true, false, false, 'VORLESUNG'),
    (5846846, true, false, true, false, true, false, false, 'VORLESUNG');

INSERT INTO modultermin (modul, termin_name, start_date, ende_date, notiz, terminart, terminfrequenz) VALUES
    (1654654, 'T1', '2025-03-23 14:30:00', '2025-04-23 14:30:00', null, null, 'EINMALIG'),
    (2546546, 'T2', '2025-03-24 14:30:00', null, null, null, 'TÄGLICH'),
    (3846846, 'T3', '2025-03-25 14:30:00', null, null, null, 'WÖCHENTLICH'),
    (4846846, 'T4', '2025-03-26 14:30:00', null, null, null, 'MONATLICH'),
    (5846846, 'T5', '2025-03-27 14:30:00', null, 'N1', null, 'JÄHRLICH'),
    (7846846, 'T6', '2024-03-30 14:30:00', '2024-04-30 14:30:00', null, null, 'EINMALIG');

