-- Добавляем проекты
INSERT INTO Project (project_name) VALUES ('Project A'), ('Project B');

-- Добавляем задачи
INSERT INTO Task (task_name, project_id) VALUES
                                             ('Task 1', 1),
                                             ('Task 2', 1),
                                             ('Task 3', 2);

-- Добавляем сотрудников
INSERT INTO Employee (employee_name) VALUES ('John Doe'), ('Jane Doe');

-- Добавляем связи между сотрудниками и задачами
INSERT INTO EmployeeTask (employee_id, task_id) VALUES
                                                    (1, 1),
                                                    (1, 2),
                                                    (2, 3);