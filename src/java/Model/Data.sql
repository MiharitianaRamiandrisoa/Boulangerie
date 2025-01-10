-- Insertion des données dans la table `Unite`
INSERT INTO Unite (nomUnite, Abreviation) VALUES 
('Kilogramme', 'kg'),
('Litre', 'l'),
('Piece', 'pc');

-- Insertion des données dans la table `TypeProduit`
INSERT INTO TypeProduit (nomType) VALUES 
('Pain'),
('Patisserie'),
('Viennoiserie');

-- Insertion des données dans la table `Produit`
INSERT INTO Produit (nomProduit, efface, idTypeProduit) VALUES 
('Baguette', FALSE, 1), 
('Croissant', FALSE, 3),
('Eclair au chocolat', FALSE, 2);

-- Insertion des données dans la table `Ingredient`
INSERT INTO Ingredient (nomIngredient, IdUnite, idTypeIngredient) VALUES 
('Farine', 1, 1),
('Beurre', 1, 1),
('Sucre', 1, 1),
('Chocolat', 1, 2);

-- Insertion des données dans la table `TypeIngredient`
INSERT INTO TypeIngredient (nom) VALUES
('parfum'),
('nature')

-- Insertion des données dans la table `PrixProduit`
INSERT INTO PrixProduit (prix, _date, idProduit) VALUES 
(0.50, '2025-01-01', 1), -- Prix pour Baguette
(0.80, '2025-01-01', 2), -- Prix pour Croissant
(1.20, '2025-01-01', 3); -- Prix pour Éclair au chocolat

-- Insertion des données dans la table `Production`
INSERT INTO Production (qtt, _date, idProduit) VALUES 
(100, '2025-01-02', 1),
(50, '2025-01-02', 2),
(30, '2025-01-02', 3);

-- Insertion des données dans la table `TypeMvt`
INSERT INTO TypeMvt (libelle) VALUES 
('Entrée'),
('Sortie');

-- Insertion des données dans la table `IngredientMvt`
INSERT INTO IngredientMvt (qtt, _date, idType, idIngredient) VALUES 
(50, '2025-01-02', 1, 1), -- 50 kg de Farine ajoutés
(20, '2025-01-02', 1, 2), -- 20 kg de Beurre ajoutés
(10, '2025-01-02', 2, 3); -- 10 kg de Sucre utilisés

-- Insertion des données dans la table `Vente`
INSERT INTO Vente (_date) VALUES 
('2025-01-02'),
('2025-01-03');

-- Insertion des données dans la table `VenteDetail`
INSERT INTO VenteDetail (idProduit, idVente , qtt) VALUES 
(1, 1 , 5 ), -- Baguette vendue lors de la première vente
(2, 1 , 5 ), -- Croissant vendu lors de la première vente
(3, 2 , 5 ); -- Éclair au chocolat vendu lors de la deuxième vente

-- Insertion des données dans la table `Recette`
INSERT INTO Recette (idProduit, idIngredient, qtt) VALUES 
(1, 1, 0.25), -- 250 g de Farine pour une Baguette
(2, 2, 0.20), -- 200 g de Beurre pour un Croissant
(3, 4, 0.30); -- 300 g de Chocolat pour un Éclair