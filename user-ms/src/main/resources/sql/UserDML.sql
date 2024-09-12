INSERT INTO users (uuid, full_name, email, password, enabled, rol_id, )
VALUES
    (uuid_generate_v4(),'admin', 'admin@mail.com',
     '$2a$10$/UUhaYhNyQGYd4R90gK/mO/d4q5lguLAXJrTAujWpOngwt.1EHzIW', true, 3)