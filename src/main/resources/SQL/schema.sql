DROP TABLE IF EXISTS employee_task CASCADE;
DROP TABLE IF EXISTS task CASCADE;
DROP TABLE IF EXISTS employee CASCADE;
DROP TABLE IF EXISTS project CASCADE;

CREATE TABLE IF NOT EXISTS project
(
    project_id   SERIAL PRIMARY KEY,
    project_name VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS task
(
    task_id    SERIAL PRIMARY KEY,
    task_name  VARCHAR(255) NOT NULL,
    project_id INTEGER REFERENCES project (project_id)
    );

CREATE TABLE IF NOT EXISTS employee
(
    employee_id   SERIAL PRIMARY KEY,
    employee_name VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS employee_task
(
    employee_id INTEGER NOT NULL REFERENCES employee (employee_id),
    task_id     INTEGER NOT NULL REFERENCES task (task_id),
    CONSTRAINT employeetask_pkey PRIMARY KEY (employee_id, task_id)
    );