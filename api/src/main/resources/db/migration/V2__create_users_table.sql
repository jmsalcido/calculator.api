CREATE TYPE user_role_type AS ENUM ('user', 'admin');
CREATE TYPE status_type AS ENUM ('active', 'trial', 'beta', 'inactive');

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    uuid     UUID DEFAULT uuid_generate_v4(),
    username VARCHAR,
    password VARCHAR,
    role     user_role_type,
    status   status_type
);
