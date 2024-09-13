-- Instalar la extensión
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Table structure for table parking
CREATE TABLE parking (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
                         name VARCHAR(100) NOT NULL,
                         max_capacity INT NOT NULL,
                         current_capacity INT NOT NULL,
                         cost_hour DECIMAL(10, 2) NOT NULL,
                         direction VARCHAR(255) NOT NULL,
                         user_id VARCHAR(255) NOT NULL
);

-- Table structure for table vehicle
CREATE TABLE vehicle (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
                         vehicle_plate VARCHAR(6) NOT NULL UNIQUE,
                         total_visit INT NOT null,
                         model VARCHAR(100) NOT NULL,
                         parking_id UUID REFERENCES parking(id) NOT NULL
);

-- Table structure for table parking_history
CREATE TABLE parking_history (
                                 id UUID PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
                                 vehicle_id UUID NOT NULL REFERENCES vehicle(id),
                                 parking_id UUID NOT NULL REFERENCES parking(id),
                                 total_cost DECIMAL(10, 2) NOT NULL,
                                 entry_date TIMESTAMP NOT NULL,
                                 exit_date TIMESTAMP NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_vehicle_id ON parking_history (vehicle_id);
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
COMMENT ON COLUMN parking_history.vehicle_id IS 'Identifier of the vehicle';
COMMENT ON COLUMN parking_history.parking_id IS 'Identifier of the parking lot';
COMMENT ON COLUMN parking_history.total_cost IS 'Total Cost of the parking lot';
COMMENT ON COLUMN parking_history.entry_date IS 'Entry date of the vehicle';
COMMENT ON COLUMN parking_history.exit_date IS 'Exit date of the vehicle';