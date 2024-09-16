-- CREATE DATABASE USERS 
CREATE DATABASE users;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Connect to the new parking database
\connect users;

-- Table structure for table rol
CREATE TABLE rol (
    id INT PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL
);

-- Table structure for table users
CREATE TABLE users (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    rol_id INT REFERENCES rol(id) NOT NULL,
    verification_code VARCHAR(6),
    verification_exp TIMESTAMP
);

-- Indexes for faster querying
CREATE INDEX idx_users_email ON users(email);

-- Comments on columns
COMMENT ON COLUMN rol.id IS 'Unique identifier for the role';
COMMENT ON COLUMN rol.name IS 'Name of the role';

COMMENT ON COLUMN users.uuid IS 'Unique identifier for the user';
COMMENT ON COLUMN users.full_name IS 'Full name of the user';
COMMENT ON COLUMN users.email IS 'Email of the user';
COMMENT ON COLUMN users.password IS 'User password';
COMMENT ON COLUMN users.enabled IS 'Whether the user is enabled or not';
COMMENT ON COLUMN users.rol_id IS 'Role of the user';
COMMENT ON COLUMN users.verification_code IS 'Verification code of the user';
COMMENT ON COLUMN users.verification_exp IS 'Expiration date of the verification code';


-- INSERT VALUE FOR USERS 
-- ROL
INSERT INTO rol (id, name) 
VALUES 
    (3, 'ADMIN'),
    (2, 'SOCIO'),
    (1, 'USER');
	

-- USERS
INSERT INTO users ("uuid", full_name, email, "password", enabled, rol_id, verification_code, verification_exp)
VALUES
	('1e66eee1-a08d-4965-b159-505ecb37ae5e', 'admin', 'admin@mail.com', '$2a$10$/UUhaYhNyQGYd4R90gK/mO/d4q5lguLAXJrTAujWpOngwt.1EHzIW', true, 3, NULL, NULL),
	('b908dc3d-f435-4dc8-b38b-d944cbef7747', 'socio1', 'socio1@gmail.com', '$2a$10$.cTiMa0OiYhBlzHYxtrG2ufh5NXCEfFDd5V639oofLxfhfO0.5vGe', true, 2, NULL, NULL),
	('1e6de391-bdbe-4e1a-a5af-dd6b04d9c033', 'socio2', 'socio2@gmail.com', '$2a$10$EUsoY7fKzUqqKpyFEP1.K.q8TjN1RFnJx5cLVVP/7L4lCSNEei.6C', true, 2,  NULL, NULL);