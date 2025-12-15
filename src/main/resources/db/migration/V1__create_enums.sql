CREATE TYPE  user_role AS ENUM (
    'ADMIN',
    'TI',
    'USER'
);

CREATE TYPE ticket_status AS ENUM (
    'OPEN',
    'IN_PROGRESS',
    'CLOSE'
);