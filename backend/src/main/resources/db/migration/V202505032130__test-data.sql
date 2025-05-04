INSERT INTO filter (name, valid_from, valid_until)
VALUES ('Test-filter1', current_timestamp, null),
       ('Random filter', current_timestamp, null),
       ('expired filter', '2023-01-01', '2024-01-01');

INSERT INTO filter_criteria (filter_id, criteria_value_type_id, value, valid_from)
VALUES ((SELECT id FROM filter WHERE name = 'Test-filter1'),
        (SELECT id FROM criteria_value_type WHERE comparator = 'More than'),
        '100', current_timestamp),
       ((SELECT id FROM filter WHERE name = 'Test-filter1'),
        (SELECT id FROM criteria_value_type WHERE comparator = 'Starts with'),
        'This story starts', current_timestamp),
       ((SELECT id FROM filter WHERE name = 'Test-filter1'),
        (SELECT id FROM criteria_value_type WHERE comparator = 'Is after'),
        '2025-01-02', current_timestamp);

INSERT INTO filter_criteria (filter_id, criteria_value_type_id, value, valid_from, valid_until)
VALUES ((SELECT id FROM filter WHERE name = 'Random filter'),
        (SELECT id FROM criteria_value_type WHERE comparator = 'More than'),
        '100', current_timestamp, null),
       ((SELECT id FROM filter WHERE name = 'Random filter'),
        (SELECT id FROM criteria_value_type WHERE comparator = 'Less than'),
        '300', '2024-01-01', '2025-01-01');
