INSERT INTO users ( email, full_name, password, role)
VALUES (
           'admin@fixwi.com',
           'admin',
           '$2a$10$Zahm7Qzft6JeK4CNEwXH5ev7Ct9xFSS39IQ4XCP8juJwFQT6sHgEW',
           'ADMIN'::user_role
       ),
       ('bany@fixwi.com',
        'bany',
        '$2a$10$u9RsNlfcCY3avWITePOSdeTpjzm2wrNhwboWWCk/Im6DM9WSV5ZXG',
        'USER'::user_role
       );