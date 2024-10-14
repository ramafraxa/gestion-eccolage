CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    class_name VARCHAR(20) NOT NULL,
    address VARCHAR(100),
    gender VARCHAR(10) NOT NULL
);

CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    student_id INT NOT NULL,
    payment_name VARCHAR(50) NOT NULL,
    payment_date DATE NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    month VARCHAR(20) NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

CREATE TABLE receipts (
    id SERIAL PRIMARY KEY,
    payment_id INT NOT NULL,
    issue_date DATE NOT NULL,
    receipt_number VARCHAR(50) NOT NULL,
    description TEXT,
    FOREIGN KEY (payment_id) REFERENCES payments(id) ON DELETE CASCADE
);

