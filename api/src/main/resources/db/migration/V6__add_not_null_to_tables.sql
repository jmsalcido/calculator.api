UPDATE users SET uuid = uuid_generate_v4() WHERE uuid IS NULL;
UPDATE services SET uuid = uuid_generate_v4() WHERE uuid IS NULL;
UPDATE record SET uuid = uuid_generate_v4() WHERE uuid IS NULL;

ALTER TABLE users ALTER COLUMN uuid SET NOT NULL;
ALTER TABLE services ALTER COLUMN uuid SET NOT NULL;
ALTER TABLE record ALTER COLUMN uuid SET NOT NULL;