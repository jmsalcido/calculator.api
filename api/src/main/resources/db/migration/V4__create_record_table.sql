CREATE TABLE record
(
    id               SERIAL PRIMARY KEY,
    uuid             UUID DEFAULT uuid_generate_v4(),
    service_id       INT,
    user_id          INT,
    cost             DOUBLE PRECISION,
    user_balance     DOUBLE PRECISION,
    service_response VARCHAR,
    date             TIMESTAMP,
    CONSTRAINT fk_service_id FOREIGN KEY (service_id) REFERENCES services (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);
