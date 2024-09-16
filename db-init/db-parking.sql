-- CREATE DATABASE PARKING 
CREATE DATABASE parking;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Connect to the new parking database
\connect parking;

-- Table structure for table parking
CREATE TABLE parking (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL,
                         name VARCHAR(100) NOT NULL,
                         max_capacity INT NOT NULL,
                         current_capacity INT NOT NULL,
                         cost_hour DECIMAL(10, 2) NOT NULL,
                         direction VARCHAR(255) NOT NULL,
                         user_id VARCHAR(255) NOT NULL
);

-- Table structure for table vehicle
CREATE TABLE vehicle (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL,
                         vehicle_plate VARCHAR(6) NOT NULL UNIQUE,
                         total_visit INT NOT null,
                         model VARCHAR(100) NOT NULL,
                         parking_id UUID REFERENCES parking(id) NOT NULL
);

-- Table structure for table parking_history
CREATE TABLE parking_history (
                                 id UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL,
                                 vehicle_plate VARCHAR(6) NOT NULL REFERENCES vehicle(vehicle_plate),
                                 parking_id UUID NOT NULL REFERENCES parking(id),
                                 total_cost DECIMAL(10, 2),
                                 entry_date TIMESTAMP,
                                 exit_date TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_vehicle_plate ON parking_history (vehicle_plate);
CREATE INDEX IF NOT EXISTS idx_parking_id ON parking_history (parking_id);

COMMENT ON COLUMN parking.id IS 'Unique identifier for the parking lot';
COMMENT ON COLUMN parking.name IS 'Name of the parking lot';
COMMENT ON COLUMN parking.max_capacity IS 'Capacity of the parking lot';
COMMENT ON COLUMN parking.cost_hour IS 'Cost per hour of the parking lot';
COMMENT ON COLUMN parking.direction IS 'Location of the parking lot';
COMMENT ON COLUMN parking.user_id IS 'Owner of the parking lot';

COMMENT ON COLUMN vehicle.id IS 'Unique identifier for the vehicle';
COMMENT ON COLUMN vehicle.total_visit IS 'Total visit for the vehicle';
COMMENT ON COLUMN vehicle.vehicle_plate IS 'Unique license plate of the vehicle';
COMMENT ON COLUMN vehicle.model IS 'Model of the vehicle';
COMMENT ON COLUMN vehicle.parking_id IS 'Parking lot where the vehicle is parked';

COMMENT ON COLUMN parking_history.id IS 'Unique identifier for the history record';
COMMENT ON COLUMN parking_history.vehicle_plate IS 'Unique license plate of the vehicle';
COMMENT ON COLUMN parking_history.parking_id IS 'Identifier of the parking lot';
COMMENT ON COLUMN parking_history.total_cost IS 'Total Cost of the parking lot';
COMMENT ON COLUMN parking_history.entry_date IS 'Entry date of the vehicle';
COMMENT ON COLUMN parking_history.exit_date IS 'Exit date of the vehicle';


-- INSERT VALUE FOR PARKING 
-- PARKING
INSERT INTO parking (id, name, max_capacity, current_capacity, cost_hour, direction, user_id)
VALUES 
	('f2a8f1b2-1234-4abc-8765-abcdef123456', 'Parking Central', 50, 5, 1200, '123 Main St, Cityville', 'socio1@gmail.com'),
	('f2a8f1b2-1234-4abc-8765-abcdef123457', 'Parking Caobos', 50, 5, 1500, 'Calle 15E', 'socio2@gmail.com');


-- VEHICLES
INSERT INTO vehicle (id, vehicle_plate, total_visit, model, parking_id)
VALUES 
('a1b2c3d4-5678-9101-1121-abcdef000001', 'ABC123', 1, 'Toyota Corolla', 'f2a8f1b2-1234-4abc-8765-abcdef123456'),
('a1b2c3d4-5678-9101-1121-abcdef000002', 'DEF456', 5, 'Honda Civic', 'f2a8f1b2-1234-4abc-8765-abcdef123456'),
('a1b2c3d4-5678-9101-1121-abcdef000003', 'GHI789', 4, 'Ford Focus', 'f2a8f1b2-1234-4abc-8765-abcdef123456'),
('a1b2c3d4-5678-9101-1121-abcdef000004', 'JKL321', 2, 'BMW 3 Series', 'f2a8f1b2-1234-4abc-8765-abcdef123456'),
('a1b2c3d4-5678-9101-1121-abcdef000005', 'MNO654', 1, 'Audi A4', 'f2a8f1b2-1234-4abc-8765-abcdef123456'),
('a1b2c3d4-5678-9101-1121-abcdef000006', 'PQR987', 7, 'Mercedes-Benz C-Class', 'f2a8f1b2-1234-4abc-8765-abcdef123457'),
('a1b2c3d4-5678-9101-1121-abcdef000007', 'STU111', 5, 'Volkswagen Passat', 'f2a8f1b2-1234-4abc-8765-abcdef123457'),
('a1b2c3d4-5678-9101-1121-abcdef000008', 'VWX222', 1, 'Chevrolet Cruze', 'f2a8f1b2-1234-4abc-8765-abcdef123457'),
('a1b2c3d4-5678-9101-1121-abcdef000009', 'YZA333', 3, 'Hyundai Elantra', 'f2a8f1b2-1234-4abc-8765-abcdef123457'),
('a1b2c3d4-5678-9101-1121-abcdef000010', 'BCD444', 4, 'Nissan Altima', 'f2a8f1b2-1234-4abc-8765-abcdef123457');

-- PARKING HISTORY
INSERT INTO parking_history (id, vehicle_plate, parking_id, total_cost, entry_date, exit_date)
VALUES
('1111aaaa-bbbb-cccc-dddd-abcdef000001', 'ABC123', 'f2a8f1b2-1234-4abc-8765-abcdef123456', 0, '2024-09-01 08:00:00', NULL),
('1111aaaa-bbbb-cccc-dddd-abcdef000002', 'DEF456', 'f2a8f1b2-1234-4abc-8765-abcdef123456', 0, '2024-09-02 09:00:00', NULL),
('1111aaaa-bbbb-cccc-dddd-abcdef000003', 'GHI789', 'f2a8f1b2-1234-4abc-8765-abcdef123456', 2400, '2024-09-03 12:00:00', '2024-09-03 14:00:00'),
('1111aaaa-bbbb-cccc-dddd-abcdef000004', 'JKL321', 'f2a8f1b2-1234-4abc-8765-abcdef123456', 2400, '2024-09-04 07:00:00', '2024-09-04 09:00:00'),
('1111aaaa-bbbb-cccc-dddd-abcdef000005', 'MNO654', 'f2a8f1b2-1234-4abc-8765-abcdef123456', 2400, '2024-09-05 15:00:00', '2024-09-05 17:00:00'),
('1111aaaa-bbbb-cccc-dddd-abcdef000006', 'PQR987', 'f2a8f1b2-1234-4abc-8765-abcdef123457', 0, '2024-09-06 11:00:00', NULL),
('1111aaaa-bbbb-cccc-dddd-abcdef000007', 'STU111', 'f2a8f1b2-1234-4abc-8765-abcdef123457', 4500, '2024-09-07 14:00:00', '2024-09-07 17:00:00'),
('1111aaaa-bbbb-cccc-dddd-abcdef000008', 'VWX222', 'f2a8f1b2-1234-4abc-8765-abcdef123457', 4500, '2024-09-08 09:00:00', '2024-09-08 12:00:00'),
('1111aaaa-bbbb-cccc-dddd-abcdef000009', 'YZA333', 'f2a8f1b2-1234-4abc-8765-abcdef123457', 3000, '2024-09-09 08:00:00', '2024-09-09 11:00:00'),
('1111aaaa-bbbb-cccc-dddd-abcdef000010', 'BCD444', 'f2a8f1b2-1234-4abc-8765-abcdef123457', 3000, '2024-09-10 07:00:00', '2024-09-10 09:00:00');