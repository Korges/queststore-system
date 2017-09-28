INSERT INTO wallets (student_id, money, experience) VALUES (1, 69, 69);
INSERT INTO wallets (student_id, money, experience) VALUES (2, 666, 666);
INSERT INTO wallets (student_id, money, experience) VALUES (4, 123, 123);
INSERT INTO wallets (student_id, money, experience) VALUES (5, 1, 1);
INSERT INTO wallets (student_id, money, experience) VALUES (8, 0, 0);


INSERT INTO quests (name, description, value, experience, quest_category) VALUES ("Wypuść bonka", "Należytym zadaniem jest wypuszczenie bonka, niechaj się błonka.", 150, 150, "theQuest");
INSERT INTO quests (name, description, value, experience, quest_category) VALUES ("Niuchnij bobra", "Udekomunetować zdjęcie niuchania bobera", 11, 11, "theQuest");
INSERT INTO quests (name, description, value, experience, quest_category) VALUES ("cleanToilet()", "Umyj kibel", 420, 420, "magicQuest");

INSERT INTO artifacts (price, description, is_magic) VALUES (999, "Berlo czystości", 1);
INSERT INTO artifacts (price, description, is_magic) VALUES (222, "Talon na balon", 0);
INSERT INTO artifacts (price, description, is_magic) VALUES (69, "Klapki kubota", 0);

INSERT INTO logs (student_id, artifact_id, price) VALUES (1, 2, 222);
INSERT INTO logs (student_id, artifact_id, price) VALUES (2, 3, 69);

INSERT INTO fundraises (artifact_id, is_collected, gathered) VALUES (1, 0, 666);

INSERT INTO fundraises_students (fundraise_id, student_id) VALUES (1,1);
INSERT INTO fundraises_students (fundraise_id, student_id) VALUES (1,2);

INSERT INTO submissions (quest_id, student_id, is_marked, description) VALUES (1,4,0, "Wypuszczone w brickroom");
INSERT INTO submissions (quest_id, student_id, is_marked, description) VALUES (3,4,1, "Umyte szczoteczką korgesa hehe XD.");
