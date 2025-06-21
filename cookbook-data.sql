DROP TABLE IF EXISTS recipe_ingredient;
DROP TABLE IF EXISTS ingredient;
DROP TABLE IF EXISTS recipe;

CREATE TABLE recipe (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    instruction TEXT,
    vegetarian BOOLEAN NOT NULL,
    servings INTEGER NOT NULL
);

CREATE TABLE ingredient (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE recipe_ingredient (
    recipe_id INTEGER REFERENCES recipe(id) ON DELETE CASCADE,
    ingredient_id INTEGER REFERENCES ingredient(id) ON DELETE CASCADE,
    PRIMARY KEY (recipe_id, ingredient_id)
);

INSERT INTO ingredient (name) VALUES
('Spaghetti'),
('Ground beef'),
('Tomato sauce'),
('Onion'),
('Garlic'),
('Quinoa'),
('Tomato'),
('Cucumber'),
('Parsley'),
('Lemon juice'),
('Eggs'),
('Bell pepper');

INSERT INTO recipe (title, description, instruction, vegetarian, servings) VALUES
('Spaghetti Bolognese', 'Traditional Italian pasta with meat sauce.',
 'Cook the spaghetti until al dente. Sauté the beef with onion and garlic. Add tomato sauce and simmer for 10 minutes. Combine with spaghetti and serve.',
 false, 4),

('Quinoa Salad', 'Healthy salad with quinoa, vegetables, and herbs.',
 'Boil the quinoa until tender. Chop the vegetables into small cubes. Mix everything and add lemon juice and olive oil.',
 true, 2),

('Vegetable Omelette', 'Quick omelette with sautéed vegetables.',
 'Beat the eggs. Sauté the vegetables. Add the eggs and cook until set.',
 true, 1);

INSERT INTO recipe_ingredient (recipe_id, ingredient_id) VALUES
(1, (SELECT id FROM ingredient WHERE name = 'Spaghetti')),
(1, (SELECT id FROM ingredient WHERE name = 'Ground beef')),
(1, (SELECT id FROM ingredient WHERE name = 'Tomato sauce')),
(1, (SELECT id FROM ingredient WHERE name = 'Onion')),
(1, (SELECT id FROM ingredient WHERE name = 'Garlic'));

INSERT INTO recipe_ingredient (recipe_id, ingredient_id) VALUES
(2, (SELECT id FROM ingredient WHERE name = 'Quinoa')),
(2, (SELECT id FROM ingredient WHERE name = 'Tomato')),
(2, (SELECT id FROM ingredient WHERE name = 'Cucumber')),
(2, (SELECT id FROM ingredient WHERE name = 'Parsley')),
(2, (SELECT id FROM ingredient WHERE name = 'Lemon juice'));

INSERT INTO recipe_ingredient (recipe_id, ingredient_id) VALUES
(3, (SELECT id FROM ingredient WHERE name = 'Eggs')),
(3, (SELECT id FROM ingredient WHERE name = 'Bell pepper')),
(3, (SELECT id FROM ingredient WHERE name = 'Onion')),
(3, (SELECT id FROM ingredient WHERE name = 'Tomato'));
