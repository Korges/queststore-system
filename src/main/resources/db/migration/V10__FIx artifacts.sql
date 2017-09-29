DROP TABLE artifacts;
CREATE TABLE `artifacts` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT,
	`description`	TEXT,
	`price`	INTEGER,
	`is_magic`	INTEGER NOT NULL
);

INSERT INTO artifacts (name,description,price, is_magic) VALUES ("Berlo czystości","czyste berlo",999, 1);
INSERT INTO artifacts (name,description,price, is_magic) VALUES ("Talon na balon","czyste berlo",222, 0);
INSERT INTO artifacts (name,description,price, is_magic) VALUES ("Klapki kubota","Kubota",69, 0);
