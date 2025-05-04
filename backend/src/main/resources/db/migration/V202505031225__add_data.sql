INSERT INTO criteria_type (name, type, is_default)
VALUES ('amount', 'number', true),
       ('title', 'string', false),
       ('date', 'date', false);

insert into criteria_value_type (criteria_type_id, comparator, default_value)
VALUES ((select id FROM criteria_type WHERE name = 'amount'), 'More than', '0'),
       ((select id FROM criteria_type WHERE name = 'amount'), 'Equal', '0'),
       ((select id FROM criteria_type WHERE name = 'amount'), 'Less than', '0'),
       ((select id FROM criteria_type WHERE name = 'title'), 'Starts with', ''),
       ((select id FROM criteria_type WHERE name = 'title'), 'Ends with', ''),
       ((select id FROM criteria_type WHERE name = 'title'), 'Contains', ''),
       ((select id FROM criteria_type WHERE name = 'date'), 'Is before', '2025-01-01'),
       ((select id FROM criteria_type WHERE name = 'date'), 'Is after', '2025-01-01'),
       ((select id FROM criteria_type WHERE name = 'date'), 'Is at', '2025-01-01');
