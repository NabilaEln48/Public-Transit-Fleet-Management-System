-- Recreate and select the transit database
DROP DATABASE IF EXISTS ptfms_db;
CREATE DATABASE ptfms_db;
USE ptfms_db;

-- Users Table: Stores login credentials and roles (Feature Ref: FR-01)
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL, -- Store hashed password in production
    role ENUM('MANAGER', 'OPERATOR') NOT NULL
);

-- Vehicles Table: Transit vehicle metadata (Feature Ref: FR-02)
CREATE TABLE transit_vehicles (
    id VARCHAR(50) PRIMARY KEY,
    category ENUM('Diesel Bus', 'Electric Light Rail', 'Diesel-Electric Train') NOT NULL,
    registration_number VARCHAR(50) UNIQUE NOT NULL,
    fuel_used VARCHAR(50) NOT NULL,
    efficiency_rate DOUBLE NOT NULL,
    capacity INT NOT NULL,
    assigned_route VARCHAR(100) NOT NULL,
    operational_state ENUM('Active', 'Inactive', 'Maintenance') DEFAULT 'Active'
);

-- Real-Time Location Tracking (Feature Ref: FR-03)
CREATE TABLE live_tracking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_ref VARCHAR(50) NOT NULL,
    gps_lat DOUBLE NOT NULL,
    gps_lng DOUBLE NOT NULL,
    recorded_at DATETIME NOT NULL,
    linked_station VARCHAR(50),
    FOREIGN KEY (vehicle_ref) REFERENCES transit_vehicles(id)
);

-- Transit stations
CREATE TABLE route_stations (
    id VARCHAR(50) PRIMARY KEY,
    station_name VARCHAR(100) NOT NULL,
    geo_location VARCHAR(255),
    associated_route VARCHAR(50) NOT NULL
);

-- Arrival/Departure logs at stations
CREATE TABLE station_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_ref VARCHAR(50) NOT NULL,
    station_ref VARCHAR(50) NOT NULL,
    action_type ENUM('ARRIVAL', 'DEPARTURE') NOT NULL,
    logged_time VARCHAR(50) NOT NULL,
    FOREIGN KEY (vehicle_ref) REFERENCES transit_vehicles(id),
    FOREIGN KEY (station_ref) REFERENCES route_stations(id)
);

-- Operator Break and Out-of-Service Log (FR-03)
CREATE TABLE service_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_ref VARCHAR(50) NOT NULL,
    break_start DATETIME NOT NULL,
    break_end DATETIME,
    log_type ENUM('Break', 'OutOfService') NOT NULL,
    operator_ref INT NOT NULL,
    FOREIGN KEY (vehicle_ref) REFERENCES transit_vehicles(id),
    FOREIGN KEY (operator_ref) REFERENCES user(id)
);

-- Fuel and Energy Monitoring Table (FR-04)
CREATE TABLE fuel_energy_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_ref VARCHAR(50) NOT NULL,
    energy_type VARCHAR(50) NOT NULL,
    quantity_used DOUBLE NOT NULL,
    km_covered DOUBLE NOT NULL,
    recorded_at DATETIME NOT NULL,
    FOREIGN KEY (vehicle_ref) REFERENCES transit_vehicles(id)
);

-- Vehicle Components and Diagnostics (FR-05)
CREATE TABLE system_components (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_ref VARCHAR(50) NOT NULL,
    component_type VARCHAR(50) NOT NULL,
    total_runtime DOUBLE NOT NULL,
    usage_wear_percent DOUBLE NOT NULL,
    status_check ENUM('Normal', 'Warning', 'Critical') DEFAULT 'Normal',
    FOREIGN KEY (vehicle_ref) REFERENCES transit_vehicles(id)
);

-- Maintenance Alerts (FR-05)
CREATE TABLE system_alerts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    alert_category VARCHAR(50) NOT NULL,
    vehicle_ref VARCHAR(50) NOT NULL,
    alert_message TEXT NOT NULL,
    alert_time DATETIME NOT NULL,
    resolution_state ENUM('Pending', 'Resolved') DEFAULT 'Pending',
    FOREIGN KEY (vehicle_ref) REFERENCES transit_vehicles(id)
);

-- Maintenance Task Scheduler (FR-05)
CREATE TABLE maintenance_schedule (
    id INT AUTO_INCREMENT PRIMARY KEY,
    component_ref INT NOT NULL,
    vehicle_ref VARCHAR(50) NOT NULL,
    task_description TEXT NOT NULL,
    planned_date DATE NOT NULL,
    progress_status ENUM('Scheduled', 'InProgress', 'Completed') DEFAULT 'Scheduled',
    assigned_technician INT,
    FOREIGN KEY (component_ref) REFERENCES system_components(id),
    FOREIGN KEY (vehicle_ref) REFERENCES transit_vehicles(id),
    FOREIGN KEY (assigned_technician) REFERENCES user(id)
);

-- Analytics and Summary Reports (FR-06)
CREATE TABLE analytics_reports (
    id INT AUTO_INCREMENT PRIMARY KEY,
    report_category VARCHAR(50) NOT NULL,
    report_payload TEXT NOT NULL,
    created_at DATETIME NOT NULL
);

-- Trips & Operator Performance Log
CREATE TABLE trip_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    operator_ref INT NOT NULL,
    vehicle_ref VARCHAR(50) NOT NULL,
    route_name VARCHAR(100) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME,
    scheduled_start DATETIME NOT NULL,
    actual_start DATETIME NOT NULL,
    on_time_flag BOOLEAN DEFAULT FALSE,
    distance_km DOUBLE NOT NULL,
    trip_length INT NOT NULL, -- minutes
    idle_minutes INT DEFAULT 0,
    fuel_spent DOUBLE,
    passengers INT,
    trip_day DATE NOT NULL,
    FOREIGN KEY (operator_ref) REFERENCES user(id),
    FOREIGN KEY (vehicle_ref) REFERENCES transit_vehicles(id)
);

-- Sample Users
INSERT INTO user (name, email, password, role) VALUES
('John Doe', 'john.doe@transit.com', 'pass123', 'Manager'),
('Jane Smith', 'jane.smith@transit.com', 'pass456', 'Operator'),
('Alice Brown', 'alice.brown@transit.com', 'pass789', 'Operator');

-- Sample Vehicles
INSERT INTO transit_vehicles (id, category, registration_number, fuel_used, efficiency_rate, capacity, assigned_route, operational_state) VALUES
('BUS001', 'Diesel Bus', 'B123', 'Diesel', 0.5, 50, 'Route A', 'Active'),
('RAIL001', 'Electric Light Rail', 'R456', 'Electricity', 2.0, 100, 'Route B', 'Active'),
('TRAIN001', 'Diesel-Electric Train', 'T789', 'Diesel', 1.5, 200, 'Route C', 'Maintenance');

-- Sample GPS logs
INSERT INTO live_tracking (vehicle_ref, gps_lat, gps_lng, recorded_at, linked_station) VALUES
('BUS001', 45.4215, -75.6972, '2025-03-27 08:00:00', 'Station1'),
('RAIL001', 45.4250, -75.6900, '2025-03-27 09:00:00', 'Station2'),
('TRAIN001', 45.4300, -75.6800, '2025-03-27 10:00:00', 'Station3');

-- Sample Operator Logs
INSERT INTO service_logs (vehicle_ref, break_start, break_end, log_type, operator_ref) VALUES
('BUS001', '2025-03-27 12:00:00', '2025-03-27 12:30:00', 'Break', 2),
('RAIL001', '2025-03-27 13:00:00', NULL, 'OutOfService', 3);

-- Sample Trip Data
INSERT INTO trip_records (operator_ref, vehicle_ref, route_name, start_time, end_time, scheduled_start, actual_start, on_time_flag, distance_km, trip_length, idle_minutes, fuel_spent, passengers, trip_day) VALUES
(2, 'BUS001', 'Route A', '2025-03-27 07:55:00', '2025-03-27 08:55:00', '2025-03-27 08:00:00', '2025-03-27 07:55:00', TRUE, 50.5, 60, 5, 12.5, 40, '2025-03-27'),
(3, 'RAIL001', 'Route B', '2025-03-27 08:10:00', '2025-03-27 09:20:00', '2025-03-27 08:00:00', '2025-03-27 08:10:00', FALSE, 45.0, 70, 10, 55.0, 65, '2025-03-27'),
(2, 'BUS001', 'Route A', '2025-03-28 07:50:00', '2025-03-28 08:50:00', '2025-03-28 08:00:00', '2025-03-28 07:50:00', TRUE, 52.0, 60, 3, 11.8, 45, '2025-03-28');

-- Sample Energy Usage
INSERT INTO fuel_energy_logs (vehicle_ref, energy_type, quantity_used, km_covered, recorded_at) VALUES
('BUS001', 'Diesel', 10.5, 20.0, '2025-03-27 08:30:00'),
('RAIL001', 'Electricity', 50.0, 25.0, '2025-03-27 09:30:00'),
('TRAIN001', 'Diesel', 30.0, 40.0, '2025-03-27 10:30:00');

-- Sample Components
INSERT INTO system_components (vehicle_ref, component_type, total_runtime, usage_wear_percent, status_check) VALUES
('BUS001', 'Brakes', 500.0, 60.0, 'Warning'),
('RAIL001', 'Pantograph', 300.0, 40.0, 'Normal'),
('TRAIN001', 'Engine', 1000.0, 80.0, 'Critical');

-- Sample Alerts
INSERT INTO system_alerts (alert_category, vehicle_ref, alert_message, alert_time, resolution_state) VALUES
('Maintenance', 'BUS001', 'Brakes need inspection', '2025-03-27 09:00:00', 'Pending'),
('Fuel', 'TRAIN001', 'Excessive fuel consumption detected', '2025-03-27 10:00:00', 'Pending');

-- Sample Maintenance Tasks
INSERT INTO maintenance_schedule (component_ref, vehicle_ref, task_description, planned_date, progress_status, assigned_technician) VALUES
(1, 'BUS001', 'Inspect and replace brakes', '2025-03-28', 'Scheduled', 1),
(3, 'TRAIN001', 'Engine overhaul', '2025-03-29', 'Scheduled', NULL);

-- Sample Reports
INSERT INTO analytics_reports (report_category, report_payload, created_at) VALUES
('Fuel Usage', 'BUS001: 10.5L, RAIL001: 50kWh', '2025-03-27 11:00:00'),
('Maintenance Cost', 'Estimated cost for BUS001: $500', '2025-03-27 12:00:00');


-- Customized
-- Maintenance Task 1: BUS001 - Brakes (Completed)
INSERT INTO maintenance_schedule (component_ref, vehicle_ref, task_description, planned_date, progress_status, assigned_technician)
VALUES (1, 'BUS001', 'Replace brake pads and perform brake system check', '2025-07-31', 'Completed', 1);

-- Maintenance Task 2: RAIL001 - Pantograph (In Progress)
INSERT INTO maintenance_schedule (component_ref, vehicle_ref, task_description, planned_date, progress_status, assigned_technician)
VALUES (2, 'RAIL001', 'Clean and inspect pantograph contacts', '2025-08-01', 'In Progress', 1);

-- Maintenance Task 3: TRAIN001 - Engine (Scheduled)
INSERT INTO maintenance_schedule (component_ref, vehicle_ref, task_description, planned_date, progress_status, assigned_technician)
VALUES (3, 'TRAIN001', 'Run diagnostic and tune engine performance', '2025-08-02', 'Scheduled', 1);
