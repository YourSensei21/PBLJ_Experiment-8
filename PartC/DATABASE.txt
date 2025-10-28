USE companydb;

-- Create the new table for storing attendance records
CREATE TABLE IF NOT EXISTS Attendance (
    RecordID INT AUTO_INCREMENT PRIMARY KEY,
    StudentID INT NOT NULL,
    AttendanceDate DATE NOT NULL,
    Status VARCHAR(10) NOT NULL,
    SubmissionTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- You could add a foreign key if you also had a Student table
-- ALTER TABLE Attendance ADD CONSTRAINT fk_student
-- FOREIGN KEY (StudentID) REFERENCES Student(StudentID);
