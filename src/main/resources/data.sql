-- Insert sample users
DELETE from users where 1=1;

INSERT INTO users (username, email, password, first_name, last_name, active)
VALUES 
    ('admin', 'admin@example.com', 'password123', 'Admin', 'User', TRUE),
    ('johndoe', 'john.doe@example.com', 'securepass', 'John', 'Doe', TRUE),
    ('janedoe', 'jane.doe@example.com', 'password456', 'Jane', 'Doe', TRUE),
    ('bobsmith', 'bob.smith@example.com', 'bobspassword', 'Bob', 'Smith', FALSE);