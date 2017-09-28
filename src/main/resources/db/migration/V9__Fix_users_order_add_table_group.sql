DROP TABLE users;
CREATE TABLE users (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  first_name TEXT,
  last_name TEXT,
  email TEXT UNIQUE NOT NULL,
  password TEXT,
  role TEXT NOT NULL,
  klass INTEGER
);

INSERT INTO users (first_name, last_name, email, password, role, klass) VALUES ('Krzysztof', 'Waligura', 's1@s.pl', 's1', 'student', 1);
INSERT INTO users (first_name, last_name, email, password, role, klass) VALUES ('Paweł', 'Waleczny', 's2@s.pl', 's2', 'student', 2);
INSERT INTO users (first_name, last_name, email, password, role, klass) VALUES ('Angela', 'Makrela', 'm1@m.pl', 'm1', 'mentor', 1);
INSERT INTO users (first_name, last_name, email, password, role, klass) VALUES ('Andrzej', 'Doppa', 's4@s.pl', 's4', 'student', 3);
INSERT INTO users (first_name, last_name, email, password, role, klass) VALUES ('Mateusz', 'Słaboręki', 's3@s.pl', 's3', 'student', 2);
INSERT INTO users (first_name, last_name, email, password, role, klass) VALUES ('Juzef', 'Gebels', 'm2@m.pl', 'm2', 'mentor', 2);
INSERT INTO users (first_name, last_name, email, password, role, klass) VALUES ('Teletubiś', 'Pedał', 'a@a.pl', 'a', 'admin', NULL);
INSERT INTO users (first_name, last_name, email, password, role, klass) VALUES ('Grzegorz', 'Brzęczyszczykiewicz', 's5@s.pl', 'ęą', 'admin', NULL);


CREATE TABLE klasses (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE
);

INSERT INTO klasses (name) values ('2016.1');
INSERT INTO klasses (name) values ('2017.1');
INSERT INTO klasses (name) values ('2017.2');
