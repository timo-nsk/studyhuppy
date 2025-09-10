-- Session 1 (user: timo)
INSERT INTO session (id, fach_id, username, titel, beschreibung)
VALUES (100000, '11111111-1111-1111-1111-111111111111', 'timo', 'Mathe-Session', 'Lernsession für Analysis');

INSERT INTO block (fach_id, modul_id, modul_name, lernzeit_seconds, pausezeit_seconds, session, session_key)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Modul A',1500, 300, 100000, 1),
    ('11111111-1111-1111-1111-111111111111', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Modul A',1200, 200, 100000, 2);


-- Session 2 (user: alex)
INSERT INTO session (id, fach_id, username, titel, beschreibung)
VALUES (100001, '22222222-2222-2222-2222-222222222222', 'alex', 'Programmieren-Session', 'Java Übung');

INSERT INTO block (fach_id, modul_id, modul_name, lernzeit_seconds, pausezeit_seconds, session, session_key)
VALUES
    ('22222222-2222-2222-2222-222222222222', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'Modul A',1800, 600, 100001, 1),
    ('22222222-2222-2222-2222-222222222222', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'Modul A',1500, 300, 100001, 2);


-- Session 3 (user: timo)
INSERT INTO session (id, fach_id, username, titel, beschreibung)
VALUES (100002, '33333333-3333-3333-3333-333333333333', 'timo', 'Bio-Session', 'Genetik Wiederholung');

INSERT INTO block (fach_id, modul_id, modul_name, lernzeit_seconds, pausezeit_seconds, session, session_key)
VALUES
    ('33333333-3333-3333-3333-333333333333', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Modul A',900, 150, 100002, 1),
    ('33333333-3333-3333-3333-333333333333', 'ffffffff-ffff-ffff-ffff-ffffffffffff', 'Modul A',1200, 200, 100002, 2);


-- Session 4 (user: alex)
INSERT INTO session (id, fach_id, username, titel, beschreibung)
VALUES (100003, '44444444-4444-4444-4444-444444444444', 'alex', 'Physik-Session', 'Mechanik und Thermodynamik');

INSERT INTO block (fach_id, modul_id, modul_name, lernzeit_seconds, pausezeit_seconds, session, session_key)
VALUES
    ('44444444-4444-4444-4444-444444444444', '99999999-9999-9999-9999-999999999999', 'Modul A',1000, 200, 100003, 1),
    ('44444444-4444-4444-4444-444444444444', 'aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee', 'Modul A',800, 150, 100003, 2);


-- Session 5 (user: timo)
INSERT INTO session (id, fach_id, username, titel, beschreibung)
VALUES (100004, '55555555-5555-5555-5555-555555555555', 'timo', 'Chemie-Session', 'Organische Chemie');

INSERT INTO block (fach_id, modul_id, modul_name, lernzeit_seconds, pausezeit_seconds, session, session_key)
VALUES
    ('55555555-5555-5555-5555-555555555555', 'bbbbbbbb-cccc-dddd-eeee-ffffffffffff', 'Modul A', 1400, 250, 100004, 1),
    ('55555555-5555-5555-5555-555555555555', 'cccccccc-dddd-eeee-ffff-000000000000', 'Modul A',1600, 300, 100004, 2);
