CREATE TABLE `submissions` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`quest_id`	INTEGER,
	`student_id`	INTEGER,
	`is_marked`	INTEGER,
	`description`	TEXT
);
