INSERT INTO restaurant (name) VALUES ('Café Java');
INSERT INTO rating (restaurant_id, score) SELECT id, 10 FROM restaurant WHERE name = 'Café Java';

INSERT INTO restaurant (name) VALUES ('Spring Restaurant');
INSERT INTO rating (restaurant_id, score) SELECT id, 10 FROM restaurant WHERE name = 'Spring Restaurant';
INSERT INTO rating (restaurant_id, score) SELECT id, 6 FROM restaurant WHERE name = 'Spring Restaurant';

INSERT INTO restaurant (name) VALUES ('Jakarta Restaurant');
INSERT INTO rating (restaurant_id, score) SELECT id, 8 FROM restaurant WHERE name = 'Jakarta Restaurant';
INSERT INTO rating (restaurant_id, score) SELECT id, 7 FROM restaurant WHERE name = 'Jakarta Restaurant';
