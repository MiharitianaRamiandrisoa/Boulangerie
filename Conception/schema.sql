create database boulangerie;
\c boulangerie;

CREATE TABLE Vente(
   idVente SERIAL,
   _date DATE NOT NULL,
   PRIMARY KEY(idVente)
);

CREATE TABLE TypeProduit(
   idTypeProduit SERIAL,
   nomType VARCHAR(50)  NOT NULL,
   PRIMARY KEY(idTypeProduit)
);

CREATE TABLE TypeMvt(
   idType SERIAL,
   libelle VARCHAR(50)  NOT NULL,
   PRIMARY KEY(idType)
);

CREATE TABLE Unite(
   IdUnite SERIAL,
   nomUnite VARCHAR(50)  NOT NULL,
   abreviation VARCHAR(50)  NOT NULL,
   PRIMARY KEY(IdUnite)
);

CREATE TABLE Parfum(
   idParfum SERIAL,
   nom VARCHAR(50)  NOT NULL,
   PRIMARY KEY(idParfum)
);

CREATE TABLE Produit(
   idProduit SERIAL,
   nomProduit VARCHAR(50)  NOT NULL,
   efface BOOLEAN NOT NULL,
   idParfum INTEGER,
   idTypeProduit INTEGER NOT NULL,
   PRIMARY KEY(idProduit),
   FOREIGN KEY(idParfum) REFERENCES Parfum(idParfum),
   FOREIGN KEY(idTypeProduit) REFERENCES TypeProduit(idTypeProduit)
);

CREATE TABLE Ingredient(
   idIngredient SERIAL,
   nomIngredient VARCHAR(50)  NOT NULL,
   IdUnite INTEGER NOT NULL,
   PRIMARY KEY(idIngredient),
   FOREIGN KEY(IdUnite) REFERENCES Unite(IdUnite)
);

CREATE TABLE PrixProduit(
   idPrixProduit SERIAL,
   prix NUMERIC(15,2)   NOT NULL,
   _date DATE NOT NULL,
   idProduit INTEGER NOT NULL,
   PRIMARY KEY(idPrixProduit),
   FOREIGN KEY(idProduit) REFERENCES Produit(idProduit)
);

CREATE TABLE Production(
   idProduction SERIAL,
   qtt NUMERIC(15,2)   NOT NULL,
   _date DATE NOT NULL,
   idProduit INTEGER NOT NULL,
   PRIMARY KEY(idProduction),
   FOREIGN KEY(idProduit) REFERENCES Produit(idProduit)
);

CREATE TABLE IngredientMvt(
   idMvt SERIAL,
   qtt NUMERIC(15,2)   NOT NULL,
   _date DATE NOT NULL,
   idType INTEGER NOT NULL,
   idIngredient INTEGER NOT NULL,
   PRIMARY KEY(idMvt),
   FOREIGN KEY(idType) REFERENCES TypeMvt(idType),
   FOREIGN KEY(idIngredient) REFERENCES Ingredient(idIngredient)
);

CREATE TABLE VenteDetail(
   idProduit INTEGER,
   idVente INTEGER,
   qtt NUMERIC(15,2)   NOT NULL,
   PRIMARY KEY(idProduit, idVente),
   FOREIGN KEY(idProduit) REFERENCES Produit(idProduit),
   FOREIGN KEY(idVente) REFERENCES Vente(idVente)
);

CREATE TABLE Recette(
   idProduit INTEGER,
   idIngredient INTEGER,
   qtt NUMERIC(15,2)   NOT NULL,
   PRIMARY KEY(idProduit, idIngredient),
   FOREIGN KEY(idProduit) REFERENCES Produit(idProduit),
   FOREIGN KEY(idIngredient) REFERENCES Ingredient(idIngredient)
);


SELECT 
   SUM(CASE 
         WHEN idType = 1 THEN qtt -- Entrée de stock
         WHEN idType = 2 THEN -qtt -- Sortie de stock
         ELSE 0 
   END) AS stockDisponible
FROM IngredientMvt
WHERE idIngredient = 1;


SELECT 
    idIngredient,
    SUM(CASE 
        WHEN idType = 1 THEN qtt -- Entrée de stock
        WHEN idType = 2 THEN -qtt -- Sortie de stock
        ELSE 0 
    END) AS stockDisponible
FROM IngredientMvt
GROUP BY idIngredient;



select v.idvente , p.idproduit produit , v._date , idparfum parfum ,idtypeproduit type ,qtt 
from ventedetail vd join produit p on vd.idproduit= p.idproduit
join vente v on vd.idvente= v.idvente where idParfum = 1  ;

SELECT v.idVente, p.idProduit produit, v._date, idParfum parfum, idTypeProduit type, qtt 
FROM VenteDetail vd
JOIN Produit p ON vd.idProduit = p.idProduit
join vente v on vd.idvente = v.idvente
WHERE 1=1
and idparfum=1


-- prix total des vente d'un produit entre 2 dates
SELECT SUM(vd.qtt * pp.prix) AS prixTotal
FROM VenteDetail vd
JOIN Produit p ON vd.idProduit = p.idProduit
JOIN PrixProduit pp ON p.idProduit = pp.idProduit
JOIN Vente v ON vd.idVente = v.idVente
WHERE pp._date <= v._date
and vd.idProduit = 1  -- Remplacez par l'id du produit que vous souhaitez tester
AND pp._date <= v._date  -- Prendre le prix valide au moment de la vente
AND v._date >= '2025-01-01'  -- Remplacez par la date de début de la plage
AND v._date <= '2025-12-31'  -- Remplacez par la date de fin de la plage
GROUP BY vd.idProduit;