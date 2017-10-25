CREATE TABLE `wallets` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`student_id`	INTEGER NOT NULL UNIQUE,
	`money`	INTEGER,
	`experience`	INTEGER
);

CREATE TABLE 'fundraises' (
    'id' INTEGER PRIMARY KEY AUTOINCREMENT,
    'artifact_id' INTEGER,
    'is_collected' INTEGER NOT NULL,
    'gathered' INTEGER
);


CREATE TABLE 'fundraises_students' (
    'fundraise_id' INTEGER NOT NULL,
    'student_id' INTEGER NOT NULL
);

CREATE TABLE 'logs' (
    'student_id' INTEGER NOT NULL,
    'artifact_id' INTEGER NOT NULL,
    'date' TEXT,
    'price' INTEGER NOT NULL
);

CREATE TABLE 'artifacts' (
    'id' INTEGER PRIMARY KEY AUTOINCREMENT,
    'price' INTEGER,
    'description' TEXT,
    'is_magic' INTEGER NOT NULL
);

CREATE TABLE `quests` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT,
	`description`	TEXT,
	`value`	INTEGER,
	`experience`	INTEGER,
	`quest_category`	TEXT NOT NULL
);
