DROP TABLE 'fundraises';
CREATE TABLE `fundraises` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`artifact_id`	INTEGER,
	`title`	TEXT
);
INSERT INTO fundraises (artifact_id, title) VALUES (1, 'Zbiórka');
INSERT INTO fundraises (artifact_id, title) VALUES (2, 'Ściepa');
