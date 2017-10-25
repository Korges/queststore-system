ALTER TABLE people ADD last_name text;
ALTER TABLE people ADD first_name text;                                                     
UPDATE people SET first_name = name;                                                                    
CREATE TABLE users(id,first_name, last_name, email, password, role, klass);                                              
INSERT INTO users SELECT id, first_name, last_name, email, password, role, klass FROM people;                                      
DROP TABLE people;  
