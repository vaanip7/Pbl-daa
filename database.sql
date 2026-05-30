CREATE DATABASE college_portal;

USE college_portal;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100),
    role VARCHAR(20)
);

CREATE TABLE assignments (
    assignment_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    deadline VARCHAR(50)
);

CREATE TABLE submissions (
    submission_id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(100),
    assignment_title VARCHAR(100),
    file_name VARCHAR(255)
);

INSERT INTO users(name,email,password,role)
VALUES
('Faculty1','faculty@gmail.com','123','faculty'),
('Student1','student@gmail.com','123','student');