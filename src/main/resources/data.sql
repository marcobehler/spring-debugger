-- Insert sample users
DELETE from customers where 1=1;

INSERT INTO customers (username, email, password, first_name, last_name, active)
VALUES 
    ('admin', 'admin@example.com', 'password123', 'Admin', 'User', TRUE),
    ('johndoe', 'john.doe@example.com', 'securepass', 'John', 'Doe', TRUE),
    ('janedoe', 'jane.doe@example.com', 'password456', 'Jane', 'Doe', TRUE),
    ('bobsmith', 'bob.smith@example.com', 'bobspassword', 'Bob', 'Smith', FALSE);



DELETE FROM whats_next where 1=1;
INSERT INTO whats_next (step_1, step_1_data) values ("Download", "Spring Debugger Plugin");