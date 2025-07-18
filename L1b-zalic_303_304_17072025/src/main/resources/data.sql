-- Departments
INSERT INTO departments (id, name) VALUES (1, 'IT');
INSERT INTO departments (id, name) VALUES (2, 'QA');

-- Employees
INSERT INTO employees (id, first_name, last_name, email, position, salary, department_id) VALUES
(1, 'Ivan', 'Developerov', 'ivan.dev@example.com', 'Developer', 3000, 1),
(2, 'Petro', 'Seniorov', 'petro.senior@example.com', 'Developer', 5000, 1),
(3, 'Olga', 'Qatest', 'olga.qa@example.com', 'QA', 3500, 2),
(4, 'Anna', 'Devjunior', 'anna.junior@example.com', 'Developer', 2000, 1);
