CREATE
OR REPLACE FUNCTION calculate_offset(page_size INT, page_number INT)
RETURNS INT AS $$
DECLARE
max_int INT = 2147483647;
BEGIN
    IF
page_size::bigint * (page_number - 1) > max_int THEN
      RETURN max_int;
ELSE
      RETURN page_size * (page_number - 1);
END IF;
END;
$$
LANGUAGE plpgsql;

CREATE TABLE criteria_type
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(256) NOT NULL,
    type       VARCHAR(128) NOT NULL,
    is_default BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE criteria_value_type
(
    id               BIGSERIAL PRIMARY KEY,
    criteria_type_id BIGINT       NOT NULL,
    comparator       VARCHAR(256) NOT NULL,
    default_value    VARCHAR(256) NOT NULL,
    CONSTRAINT fk_criteria_type_id
        FOREIGN KEY (criteria_type_id)
            REFERENCES criteria_type (id)
);

CREATE TABLE filter
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(256)             NOT NULL,
    valid_from  TIMESTAMP WITH TIME ZONE NOT NULL,
    valid_until TIMESTAMP WITH TIME ZONE
);

CREATE TABLE filter_criteria
(
    id                     BIGSERIAL PRIMARY KEY,
    filter_id              BIGINT                   NOT NULL,
    criteria_value_type_id BIGINT                   NOT NULL,
    value                  VARCHAR(256)             NOT NULL,
    valid_from             TIMESTAMP WITH TIME ZONE NOT NULL,
    valid_until            TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_filter_id
        FOREIGN KEY (filter_id)
            REFERENCES filter (id),
    CONSTRAINT fk_criteria_value_type_id
        FOREIGN KEY (criteria_value_type_id)
            REFERENCES criteria_value_type (id)
);
