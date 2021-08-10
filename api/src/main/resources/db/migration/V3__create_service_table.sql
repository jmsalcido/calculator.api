CREATE TYPE service_type AS ENUM (
    'addition',
    'subtraction',
    'multiplication',
    'division',
    'square_root',
    'free_form',
    'random_string'
    );

CREATE TABLE services
(
    id     SERIAL PRIMARY KEY,
    uuid   UUID DEFAULT uuid_generate_v4(),
    type   service_type,
    cost   DOUBLE PRECISION,
    status status_type
);
