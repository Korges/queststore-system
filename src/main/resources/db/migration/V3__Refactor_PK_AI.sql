CREATE TABLE temp_table as SELECT * FROM users;
DROP TABLE users;
CREATE TABLE users (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  first_name text,
  last_name text,
  email text,
  password text,
  role text,
  klass text
);
INSERT INTO users (first_name, last_name, email, password, role, klass) select first_name, last_name, email, password, role, klass from temp_table;
DROP TABLE temp_table;



