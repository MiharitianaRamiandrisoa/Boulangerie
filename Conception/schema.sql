create database boulangerie;
\c boulangerie;


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


CREATE TABLE Genre(
   idGenre SERIAL,
   genre VARCHAR(50)  NOT NULL,
   PRIMARY KEY(idGenre)
);

CREATE TABLE Client(
   idClient SERIAL,
   nom VARCHAR(50)  NOT NULL,
   PRIMARY KEY(idClient)
);

CREATE TABLE Commission(
   idCommission SERIAL,
   _date DATE NOT NULL,
   montant NUMERIC(15,2)   NOT NULL,
   PRIMARY KEY(idCommission)
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
   qtt INTEGER NOT NULL,
   _date DATE NOT NULL,
   idProduit INTEGER NOT NULL,
   PRIMARY KEY(idProduction),
   FOREIGN KEY(idProduit) REFERENCES Produit(idProduit)
);

CREATE TABLE IngredientMvt(
   idMvt SERIAL,
   qtt INTEGER NOT NULL,
   _date DATE NOT NULL,
   idType INTEGER NOT NULL,
   idIngredient INTEGER NOT NULL,
   PRIMARY KEY(idMvt),
   FOREIGN KEY(idType) REFERENCES TypeMvt(idType),
   FOREIGN KEY(idIngredient) REFERENCES Ingredient(idIngredient)
);

CREATE TABLE Conseil(
   idConseil SERIAL,
   _date DATE NOT NULL,
   idProduit INTEGER NOT NULL,
   PRIMARY KEY(idConseil),
   UNIQUE(idProduit),
   FOREIGN KEY(idProduit) REFERENCES Produit(idProduit)
);

CREATE TABLE Vendeur(
   idVendeur SERIAL,
   nom VARCHAR(50)  NOT NULL,
   idGenre INTEGER NOT NULL,
   PRIMARY KEY(idVendeur),
   FOREIGN KEY(idGenre) REFERENCES Genre(idGenre)
);

CREATE TABLE Vente(
   idVente SERIAL,
   _date DATE NOT NULL,
   idVendeur INTEGER NOT NULL,
   idClient INTEGER NOT NULL,
   PRIMARY KEY(idVente),
   FOREIGN KEY(idVendeur) REFERENCES Vendeur(idVendeur),
   FOREIGN KEY(idClient) REFERENCES Client(idClient)
);

CREATE TABLE VenteDetail(
   idProduit INTEGER,
   idVente INTEGER,
   qtt INTEGER NOT NULL,
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
    g.genre AS GenreVendeur,
    SUM(
        CASE 
            WHEN total_ventes.totalVente >= 7
            THEN total_ventes.totalVente * (SELECT montant FROM commission ORDER BY _date DESC LIMIT 1) / 100
            ELSE 0 
        END
    ) AS sommeCommission
FROM ventedetail vd
JOIN prixproduit pp ON vd.idproduit = pp.idproduit
JOIN vente v ON vd.idvente = v.idvente
JOIN (
    SELECT 
        vd.idvente,
        SUM(pp.prix * vd.qtt) AS totalVente
    FROM ventedetail vd
    JOIN prixproduit pp ON vd.idproduit = pp.idproduit
    GROUP BY vd.idvente
) AS total_ventes ON vd.idvente = total_ventes.idvente
JOIN vendeur ve ON v.idvendeur = ve.idvendeur
JOIN genre g ON ve.idGenre = g.idGenre
GROUP BY g.genre;


WITH VenteCommission AS (
    SELECT 
        g.genre AS GenreVendeur,
        SUM(pp.prix * vd.qtt) AS totalVente,
        (SELECT montant FROM commission ORDER BY _date DESC LIMIT 1) AS pourcentageCommission
    FROM ventedetail vd
    JOIN prixproduit pp ON vd.idproduit = pp.idproduit
    JOIN vente v ON vd.idvente = v.idvente
    JOIN vendeur ve ON v.idvendeur = ve.idvendeur
    JOIN genre g ON ve.idGenre = g.idGenre
    GROUP BY g.genre
)
SELECT 
    GenreVendeur,
    SUM(totalVente * pourcentageCommission / 100) AS sommeCommission
FROM VenteCommission
GROUP BY GenreVendeur;