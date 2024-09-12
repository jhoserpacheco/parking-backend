
-- Table structure for table rol
CREATE TABLE rol (
                     id INT PRIMARY KEY NOT NULL,
                     name VARCHAR(100) NOT NULL
);

-- Table structure for table users
CREATE TABLE users (
                       uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
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

-- Example transaction to ensure the script's atomicity
BEGIN;

-- Insert example data if necessary

COMMIT;

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