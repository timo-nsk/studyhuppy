INSERT INTO session_beendet_event(id, event_id, username, beendet_datum, abgebrochen) VALUES
    (10000, '11111111-1111-1111-1111-111111111111', 'peter66', '2023-10-01 10:00:00', false),
    (10001, '11111111-1111-1111-1111-111111111112', 'maria88', '2023-10-02 11:30:00', true),
    (10003, '11111111-1111-1111-1111-111111111113', 'john_doe', '2023-10-03 14:15:00', false),
    (10004, '11111111-1111-1111-1111-111111111114', 'john_doe', '2023-10-04 10:30:00', false),
    (10005, '11111111-1111-1111-1111-111111111115', 'peter66', '2023-10-05 16:22:00', false),
    (10006, '11111111-1111-1111-1111-111111111116', 'john_doe', '2023-10-06 12:50:00', true);

INSERT INTO session_bewertung(session_beendet_event, konzentration_bewertung, produktivitaet_bewertung, schwierigkeit_bewertung) VALUES
    (10000, 5.5, 6.0, 3.5),
    (10001, 7.0, 6.5, 4.0),
    (10003, 6.5, 5.5, 2.5),
    (10004, 7.0, 4.5, 3.0),
    (10005, 9.0, 8.0, 3.0),
    (10006, 8.0, 5.5, 3.0);