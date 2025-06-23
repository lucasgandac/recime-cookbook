DROP TABLE IF EXISTS recipe_ingredients;
DROP TABLE IF EXISTS recipe;

CREATE TABLE recipe (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    instruction TEXT,
    vegetarian BOOLEAN NOT NULL,
    servings INTEGER NOT NULL
);
CREATE TABLE recipe_ingredient (
    recipe_id INTEGER REFERENCES recipe(id) ON DELETE CASCADE,
    ingredient VARCHAR(255)
);

CREATE INDEX idx_recipe_title ON recipe(title);

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

INSERT INTO recipe_ingredient (recipe_id, ingredient) VALUES
(1, 'spaghetti'),
(1, 'ground beef'),
(1, 'tomato sauce'),
(1, 'onion'),
(1, 'garlic');

INSERT INTO recipe_ingredient (recipe_id, ingredient) VALUES
(2, 'quinoa'),
(2, 'tomato'),
(2, 'cucumber'),
(2, 'parsley'),
(2, 'lemon juice');

INSERT INTO recipe_ingredient (recipe_id, ingredient) VALUES
(3, 'eggs'),
(3, 'bell pepper'),
(3, 'onion'),
(3, 'tomato');
