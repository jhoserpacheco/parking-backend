-- CREATE DATABASE MAIL 
CREATE DATABASE email;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Connect to the new parking database
\connect email;

-- Table structure for table emails
CREATE TABLE emails (
	id uuid DEFAULT gen_random_uuid() NOT NULL,
	from_email varchar(255) NOT NULL,
	to_email varchar(255) NOT NULL,
	subject varchar(255) NOT NULL,
	body text NULL,
	status varchar(50) NOT NULL,
	created_at timestamp NOT NULL,
	CONSTRAINT emails_pkey PRIMARY KEY (id)
);

-- Table structure for table email_parking
CREATE TABLE email_parking (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    from_email VARCHAR(255) NOT NULL,
    to_email VARCHAR(255) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    text TEXT NOT NULL,
    vehicle_plate VARCHAR(50) NOT NULL,
    parking_name VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL
);

-- Indexes for faster querying
CREATE INDEX idx_emailparking_id ON email_parking(id);

-- Comments on columns for table emails
COMMENT ON COLUMN emails.id IS 'Unique identifier for the email';
COMMENT ON COLUMN emails.from_email IS 'From email address';
COMMENT ON COLUMN emails.to_email IS 'To email address';
COMMENT ON COLUMN emails.subject IS 'Subject line of the email';
COMMENT ON COLUMN emails.body IS 'Body content of the email';
COMMENT ON COLUMN emails.status IS 'Current status of the email (e.g., SENT, FAILED)';
COMMENT ON COLUMN emails.created_at IS 'Timestamp when the email was created';

-- Comments on columns for table email_parking
COMMENT ON COLUMN email_parking.id IS 'Unique identifier for the email parking record';
COMMENT ON COLUMN email_parking.from_email IS 'From email address';
COMMENT ON COLUMN email_parking.to_email IS 'To email address';
COMMENT ON COLUMN email_parking.subject IS 'Subject line of the email';
COMMENT ON COLUMN email_parking.text IS 'Content of the email';
COMMENT ON COLUMN email_parking.vehicle_plate IS 'License plate of the vehicle';
COMMENT ON COLUMN email_parking.parking_name IS 'Name of the parking facility';
COMMENT ON COLUMN email_parking.status IS 'Current status of the email (e.g., SENT, FAILED)';



INSERT INTO email_parking(id, from_email, to_email, subject, "text", vehicle_plate, parking_name, status)
VALUES
	('cbe331ae-7d04-4259-88ea-4e63a28abe87', 'capacitacionesufps@gmail.com', 'socio1@gmail.com', 'Vehicle registed', 'Este es un mensaje de prueba para el body', 'ABC123', 'Parking Central', 'SENT');