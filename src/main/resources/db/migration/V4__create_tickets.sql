CREATE TABLE tickets (
                         id BIGSERIAL PRIMARY KEY,
                         title VARCHAR(150) NOT NULL,
                         description TEXT,
                         status ticket_status NOT NULL,
                         create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         update_date TIMESTAMP,
                         category_id BIGINT NOT NULL,
                         user_id BIGINT NOT NULL,

                         CONSTRAINT fk_ticket_category
                             FOREIGN KEY (category_id)
                                 REFERENCES categories(id),

                         CONSTRAINT fk_ticket_user
                             FOREIGN KEY (user_id)
                                 REFERENCES users(id)
);