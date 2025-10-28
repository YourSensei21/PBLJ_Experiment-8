CREATE DATABASE IF NOT EXISTS companydb;

USE companydb;

CREATE TABLE IF NOT EXISTS Employee (
    EmpID INT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Salary DECIMAL(10, 2)
);

INSERT INTO Employee (EmpID, Name, Salary) VALUES
(101, 'Alice Smith', 75000.00),
(102, 'Bob Johnson', 82000.00),
(103, 'Charlie Brown', 68000.00),
(104, 'Diana Prince', 90000.00);
