USE ptfms_db;
-- Maintenance Task 1: BUS001 - Brakes (Completed)
INSERT INTO maintenance_schedule (component_ref, vehicle_ref, task_description, planned_date, progress_status, assigned_technician)
VALUES (1, 'BUS001', 'Replace brake pads and perform brake system check', '2025-07-31', 'Completed', 4);

-- Maintenance Task 2: RAIL001 - Pantograph (In Progress)
INSERT INTO maintenance_schedule (component_ref, vehicle_ref, task_description, planned_date, progress_status, assigned_technician)
VALUES (2, 'RAIL001', 'Clean and inspect pantograph contacts', '2025-08-01', 'InProgress', 4);

-- Maintenance Task 3: TRAIN001 - Engine (Scheduled)
INSERT INTO maintenance_schedule (component_ref, vehicle_ref, task_description, planned_date, progress_status, assigned_technician)
VALUES (3, 'TRAIN001', 'Run diagnostic and tune engine performance', '2025-08-02', 'Scheduled', 4);
