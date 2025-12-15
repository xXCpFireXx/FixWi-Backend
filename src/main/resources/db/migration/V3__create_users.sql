CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       email VARCHAR(150) NOT NULL UNIQUE,
                       full_name VARCHAR(150) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role user_role NOT NULL
);