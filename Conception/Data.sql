-- Ajouter des données dans la table Client
INSERT INTO Client (nom) VALUES
('Jean Martin'),
('Claire Dubois'),
('Alex Lefevre'),
('Sophie Bernard');


INSERT INTO Genre(genre) VALUES
('masculin'),
('femminin');

INSERT INTO Vendeur (nom, idGenre) VALUES
('Lucien Durand', 1),
('Marie Lopez', 2),
('Alex Morel', 1);



-- Insertion des données dans la table Vente avec idVendeur
INSERT INTO Vente (_date, idClient, idVendeur) VALUES
('2025-01-01', 1, 1),
('2025-01-02', 2, 2),
('2025-01-03', 3, 3),
('2025-01-04', 4, 1),
('2025-01-05', 1, 2),
('2025-01-06', 1, 3),
('2025-01-07', 2, 2),
('2025-01-16', 3, 3),
('2025-01-16', 4, 1),
('2025-01-16', 2, 2);


-- Données pour TypeProduit
INSERT INTO TypeProduit (nomType) VALUES
('Pain'),
('Viennoiserie'),
('Patisserie'),
('Sandwich'),
('Boisson');

-- Données pour TypeMvt
INSERT INTO TypeMvt (libelle) VALUES
('Entree de stock'),
('Sortie de stock');

-- Données pour Unite
INSERT INTO Unite (nomUnite, abreviation) VALUES
('Kilogramme', 'kg'),
('Grammes', 'g'),
('Litre', 'L'),
('Pièce', 'pc');

-- Données pour Parfum
INSERT INTO Parfum (nom) VALUES
('Nature'),
('Chocolat'),
('Vanille'),
('Fraise'),
('Pistache'),
('Citron'),
('Noix de coco'),
('Caramel');

-- Données pour Produit
INSERT INTO Produit (nomProduit, efface, idParfum, idTypeProduit) VALUES
('Baguette', FALSE, 1, 1),
('Croissant', FALSE, 2, 2),
('Eclair', FALSE, 3, 3),
('Tarte aux fraises', FALSE, 4, 3),
('Macaron pistache', FALSE, 5, 3),
('Boisson citronnee', FALSE, 6, 5),
('Cake noix de coco', FALSE, 7, 3),
('Caramel beurre sale', FALSE, 8, 3);

-- Données pour Ingredient
INSERT INTO Ingredient (nomIngredient, IdUnite) VALUES
('Farine', 1),
('Sucre', 1),
('Beurre', 1),
('oeuf', 4),
('Chocolat', 2),
('Lait', 3);

-- Données pour PrixProduit
INSERT INTO PrixProduit (prix, _date, idProduit) VALUES
(1.50, '2025-01-01', 1),
(0.90, '2025-01-01', 2),
(2.50, '2025-01-01', 3),
(3.00, '2025-01-01', 4),
(1.80, '2025-01-01', 5),
(0.50, '2025-01-01', 6),
(2.20, '2025-01-01', 7),
(3.50, '2025-01-01', 8);

-- Données pour Production
INSERT INTO Production (qtt, _date, idProduit) VALUES
(100, '2025-01-01', 1),
(200, '2025-01-01', 2),
(150, '2025-01-01', 3),
(80, '2025-01-01', 4),
(120, '2025-01-01', 5),
(500, '2025-01-01', 6),
(60, '2025-01-01', 7),
(30, '2025-01-01', 8);

-- Données pour IngredientMvt
INSERT INTO IngredientMvt (qtt, _date, idType, idIngredient) VALUES
(50, '2025-01-16', 1, 1),
(20, '2025-01-16', 1, 2),
(15, '2025-01-16', 1, 3),
(30, '2025-01-16', 1, 4),
(10, '2025-01-16', 1, 5),
(5, '2025-01-01', 1, 6),
(10, '2025-01-02', 2, 1),
(5, '2025-01-02', 2, 2);

-- Données pour VenteDetail
INSERT INTO VenteDetail (idProduit, idVente, qtt) VALUES
(1, 1, 2),
(2, 1, 5),
(3, 2, 3),
(4, 2, 1),
(5, 3, 4),
(6, 3, 2),
(7, 4, 1),
(8, 4, 1),
(1, 5, 10),
(3, 5, 6);
    
-- Données pour Recette
INSERT INTO Recette (idProduit, idIngredient, qtt) VALUES
(1, 1, 0.5),
(2, 1, 0.3),
(2, 3, 0.2),
(3, 2, 0.1),
(3, 4, 1),
(4, 5, 0.05),
(5, 6, 0.1),
(6, 3, 0.5);


-- Table Commission
INSERT INTO Commission (_date, montant) VALUES
('2025-01-01',5 ),
('2025-01-15',10 ),
('2025-02-01',20 );
