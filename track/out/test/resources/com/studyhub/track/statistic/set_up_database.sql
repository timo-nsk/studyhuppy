INSERT INTO statistic (id, date, fach_id)
VALUES
    (1, '2024-02-10', '550e8400-e29b-41d4-a716-446655440200');

INSERT INTO study_interval (start, finish, modul_name, statistic, statistic_key)
VALUES
    ('2024-02-10 08:00:00', '2024-02-10 10:00:00', 'MODUL 1', 1, 0),
    ('2024-02-10 09:00:00', '2024-02-11 11:00:00', 'MODUL 1', 1, 1),
    ('2024-02-10 10:00:00', '2024-02-12 12:00:00', 'MODUL 1', 1, 2),
    ('2024-02-10 11:00:00', '2024-02-12 13:00:00', 'MODUL 2', 1, 3);

