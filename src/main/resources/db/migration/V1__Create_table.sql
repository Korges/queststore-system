CREATE TABLE `people` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT NOT NULL,
	`password`	TEXT,
	`email`	TEXT NOT NULL,
	`role`	TEXT NOT NULL,
	`klass`	TEXT
);

