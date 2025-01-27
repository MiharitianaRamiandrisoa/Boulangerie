package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Produit {
    private int idProduit;
    private String nomProduit;
    private int idTypeProduit;
    private Double prix; // Ajouter un attribut prix
    // List<Ingredient> ingredient;
    private int idParfum;

    // Constructeur vide
    public Produit() {
    }

    // Constructeur avec paramètres
    public Produit(int idProduit, String nomProduit, int idTypeProduit, Double prix) {
        this.idProduit = idProduit;
        this.nomProduit = nomProduit;
        this.idTypeProduit = idTypeProduit;
        this.prix = prix;
    }

    public Produit(String nomProduit, int idTypeProduit, Double prix, int idParfum) {
        this.nomProduit = nomProduit;
        this.idTypeProduit = idTypeProduit;
        this.prix = prix;
        this.idParfum = idParfum;
    }

    public int getIdParfum() {
        return idParfum;
    }

    public void setIdParfum(int idParfum) {
        this.idParfum = idParfum;
    }

    // Getters et Setters
    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public int getIdTypeProduit() {
        return idTypeProduit;
    }

    public void setIdTypeProduit(int idTypeProduit) {
        this.idTypeProduit = idTypeProduit;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public static List<Produit> getConseil(int mois, int annee, Connection connection) throws Exception {
        connection = (connection != null) ? connection : new PGConnect().getConnection();

        Date d = new Date(annee, mois, 1);
         List<Produit> produits = new ArrayList<>();
    String query = "SELECT p.* " +
                   "FROM Produit p  " +
                   "JOIN Conseil c ON p.idProduit = c.idProduit " +
                   "WHERE EXTRACT(YEAR FROM c._date) = ?";
        if (mois != -1) {
            query +=  " and EXTRACT(MONTH FROM c._date) = ?";
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            if(mois!= -1){
                stmt.setInt(1, annee);
                stmt.setInt(2, mois);  
            }else{
                stmt.setInt(1, annee);
            }
        
            
                ResultSet resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                int idProduit = resultSet.getInt("idProduit");
                String nomProduit = resultSet.getString("nomProduit");
                int idTypeProduit = resultSet.getInt("idTypeProduit");

                // Récupérer le prix le plus récent du produit
                Double prix = null;
                String queryPrix = "SELECT prix FROM PrixProduit WHERE idProduit = ? ORDER BY _date DESC LIMIT 1";
                try (PreparedStatement stmtPrix = connection.prepareStatement(queryPrix)) {
                    stmtPrix.setInt(1, idProduit);
                    try (ResultSet resultSetPrix = stmtPrix.executeQuery()) {
                        if (resultSetPrix.next()) {
                            prix = resultSetPrix.getDouble("prix");
                        }
                    }
                }

                produits.add(new Produit(idProduit, nomProduit, idTypeProduit, prix));
            }
        }
        return produits;
    }

    public static void insertConseil(int idProduit, int mois, int annee, Connection connection) throws SQLException {
        connection = (connection != null) ? connection : new PGConnect().getConnection();

        // Construire une chaîne de date au format 'YYYY-MM-DD'
        String dateStr = String.format("%04d-%02d-01", annee, mois); // Jour fixé à 1
        java.sql.Date date = java.sql.Date.valueOf(dateStr);

        // Vérifier si le produit conseil existe déjà pour ce mois et cette année
        String checkSql = "SELECT COUNT(*) FROM Conseil WHERE idProduit = ? AND _date = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setInt(1, idProduit);
            checkStmt.setDate(2, date);

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // Le produit conseil existe déjà pour ce mois et cette année
                    System.out.println("Le produit conseil est déjà inséré pour ce mois et cette année.");
                    return;
                }
            }
        }

        // Insérer le nouveau produit conseil
        String insertSql = "INSERT INTO Conseil (idProduit, _date) VALUES (?, ?)";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
            insertStmt.setInt(1, idProduit);
            insertStmt.setDate(2, date);

            // Exécuter l'insertion
            insertStmt.executeUpdate();
        }
    }


    public void insert(Connection connection) throws SQLException {
        connection = (connection != null) ? connection : new PGConnect().getConnection();

        // Insérer le produit dans la table Produit
        String queryProduit = "INSERT INTO Produit(nomProduit, idTypeProduit, efface) VALUES(?, ?, false)";
        try (PreparedStatement stmtProduit = connection.prepareStatement(queryProduit,
                Statement.RETURN_GENERATED_KEYS)) {
            stmtProduit.setString(1, this.nomProduit);
            stmtProduit.setInt(2, this.idTypeProduit);
            stmtProduit.executeUpdate();

            // Récupérer le dernier ID inséré pour l'associer au prix
            ResultSet generatedKeys = stmtProduit.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.idProduit = generatedKeys.getInt(1);
            }
        }

        // Insérer le prix du produit dans la table PrixProduit
        String queryPrix = "INSERT INTO PrixProduit(prix, _date, idProduit) VALUES(?, CURRENT_DATE, ?)";
        try (PreparedStatement stmtPrix = connection.prepareStatement(queryPrix)) {
            stmtPrix.setDouble(1, this.prix);
            stmtPrix.setInt(2, this.idProduit);
            stmtPrix.executeUpdate();
        }
    }

    public static String getNomProduit(int idProduit, Connection connection) throws SQLException {
        connection = (connection != null) ? connection : new PGConnect().getConnection();
        String query = "SELECT nomProduit FROM Produit WHERE idProduit = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idProduit);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("nomProduit");
                }
            }
        }
        return "Produit inconnu";
    }

    public static List<Ingredient> getIngredientsByProduit(Connection connection, int idProduit) throws Exception {
        // Vérifier si la connexion est nulle
        connection = (connection != null) ? connection : new PGConnect().getConnection();

        // Liste pour stocker les ingrédients
        List<Ingredient> ingredients = new ArrayList<>();

        // Requête pour récupérer les ingrédients liés à un produit via la table Recette
        String query = "SELECT i.idIngredient, i.nomIngredient, r.qtt " +
                "FROM Recette r " +
                "JOIN Ingredient i ON r.idIngredient = i.idIngredient " +
                "WHERE r.idProduit = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Associer l'ID du produit à la requête
            stmt.setInt(1, idProduit);
            System.out.println(idProduit);

            // Exécuter la requête
            try (ResultSet resultSet = stmt.executeQuery()) {
                // Parcourir les résultats et créer des objets Ingredient
                while (resultSet.next()) {
                    int idIngredient = resultSet.getInt("idIngredient");
                    String nomIngredient = resultSet.getString("nomIngredient");
                    int quantite = resultSet.getInt("qtt");

                    // Créer un nouvel objet Ingredient
                    Ingredient ingredient = new Ingredient(idIngredient, nomIngredient, quantite);

                    // Ajouter l'ingrédient à la liste
                    ingredients.add(ingredient);
                }
            }
        } catch (SQLException e) {
            // Gestion des erreurs SQL
            e.printStackTrace();
            throw new Exception("Erreur lors de la récupération des ingrédients : " + e.getMessage());
        }

        return ingredients;
    }

    public void getById(int idProduit, Connection connection) throws SQLException {
        connection = (connection != null) ? connection : new PGConnect().getConnection();

        String queryProduit = "SELECT * FROM Produit WHERE idProduit = ?";
        try (PreparedStatement stmtProduit = connection.prepareStatement(queryProduit)) {
            stmtProduit.setInt(1, idProduit);
            try (ResultSet resultSetProduit = stmtProduit.executeQuery()) {
                if (resultSetProduit.next()) {
                    this.idProduit = resultSetProduit.getInt("idProduit");
                    this.nomProduit = resultSetProduit.getString("nomProduit");
                    this.idTypeProduit = resultSetProduit.getInt("idTypeProduit");
                }
            }
        }

        // Récupérer le prix du produit depuis la table PrixProduit
        String queryPrix = "SELECT prix FROM PrixProduit WHERE idProduit = ? ORDER BY _date DESC LIMIT 1";
        try (PreparedStatement stmtPrix = connection.prepareStatement(queryPrix)) {
            stmtPrix.setInt(1, idProduit);
            try (ResultSet resultSetPrix = stmtPrix.executeQuery()) {
                if (resultSetPrix.next()) {
                    this.prix = resultSetPrix.getDouble("prix");
                }
            }
        }
    }

    public void update(Connection connection) throws SQLException {
        connection = (connection != null) ? connection : new PGConnect().getConnection();

        // Mettre à jour les informations du produit
        String queryProduit = "UPDATE Produit SET nomProduit = ?, idTypeProduit = ? WHERE idProduit = ?";
        try (PreparedStatement stmtProduit = connection.prepareStatement(queryProduit)) {
            stmtProduit.setString(1, this.nomProduit);
            stmtProduit.setInt(2, this.idTypeProduit);
            stmtProduit.setInt(3, this.idProduit);
            stmtProduit.executeUpdate();
        }

        // Mettre à jour le prix du produit dans la table PrixProduit
        String queryPrix = "INSERT INTO PrixProduit(prix, _date, idProduit) VALUES(?, CURRENT_DATE, ?)";
        try (PreparedStatement stmtPrix = connection.prepareStatement(queryPrix)) {
            stmtPrix.setDouble(1, this.prix);
            stmtPrix.setInt(2, this.idProduit);
            stmtPrix.executeUpdate();
        }
    }

    public static void delete(int id, Connection connection) throws SQLException {
        connection = (connection != null) ? connection : new PGConnect().getConnection();

        String query = "UPDATE Produit SET efface = true WHERE idProduit = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Fonction pour lister tous les produits
    public static List<Produit> getAllProduits(Connection connection) throws Exception {
        connection = (connection != null) ? connection : new PGConnect().getConnection();

        List<Produit> produits = new ArrayList<>();
        String query = "SELECT * FROM Produit WHERE efface = false";
        try (PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                int idProduit = resultSet.getInt("idProduit");
                String nomProduit = resultSet.getString("nomProduit");
                int idTypeProduit = resultSet.getInt("idTypeProduit");

                // Récupérer le prix le plus récent du produit
                Double prix = null;
                String queryPrix = "SELECT prix FROM PrixProduit WHERE idProduit = ? ORDER BY _date DESC LIMIT 1";
                try (PreparedStatement stmtPrix = connection.prepareStatement(queryPrix)) {
                    stmtPrix.setInt(1, idProduit);
                    try (ResultSet resultSetPrix = stmtPrix.executeQuery()) {
                        if (resultSetPrix.next()) {
                            prix = resultSetPrix.getDouble("prix");
                        }
                    }
                }
                produits.add(new Produit(idProduit, nomProduit, idTypeProduit, prix));
            }
        }
        return produits;
    }

    // Fonction pour obtenir les produits filtrés
    public static List<Produit> getProduitsFiltered(String nom, Double prixMin, Double prixMax, Integer typeProduit,
            Integer ingredient, Connection connection) throws SQLException {
        connection = (connection != null) ? connection : new PGConnect().getConnection();

        List<Produit> produits = new ArrayList<>();

        // Construction de la requête avec les filtres
        StringBuilder query = new StringBuilder();
        query.append("SELECT DISTINCT p.* ");
        query.append("FROM Produit p ");
        query.append("LEFT JOIN PrixProduit pp ON p.idProduit = pp.idProduit ");
        query.append("LEFT JOIN Recette r ON p.idProduit = r.idProduit ");
        query.append("WHERE p.efface = false "); // Ajouter cette condition pour exclure les produits marqués comme
                                                 // supprimés

        // Ajouter des conditions en fonction des paramètres
        if (nom != null && !nom.isEmpty()) {
            query.append(" AND p.nomProduit LIKE ?");
        }
        if (prixMin != null) {
            query.append(" AND pp.prix >= ?");
        }
        if (prixMax != null) {
            query.append(" AND pp.prix <= ?");
        }
        if (typeProduit != null) {
            query.append(" AND p.idTypeProduit = ?");
        }
        if (ingredient != null) {
            query.append(" AND r.idIngredient = ?");
        }

        try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
            int paramIndex = 1;

            // Ajouter les paramètres dans l'ordre
            if (nom != null && !nom.isEmpty()) {
                stmt.setString(paramIndex++, "%" + nom + "%");
            }
            if (prixMin != null) {
                stmt.setDouble(paramIndex++, prixMin);
            }
            if (prixMax != null) {
                stmt.setDouble(paramIndex++, prixMax);
            }
            if (typeProduit != null) {
                stmt.setInt(paramIndex++, typeProduit);
            }
            if (ingredient != null) {
                stmt.setInt(paramIndex++, ingredient);
            }
            System.out.println("query: " + query);

            // Exécuter la requête
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    int idProduit = resultSet.getInt("idProduit");
                    String nomProduit = resultSet.getString("nomProduit");
                    int idTypeProduit = resultSet.getInt("idTypeProduit");

                    // Récupérer le prix
                    Double prix = null;
                    String queryPrix = "SELECT prix FROM PrixProduit WHERE idProduit = ? ORDER BY _date DESC LIMIT 1";
                    try (PreparedStatement stmtPrix = connection.prepareStatement(queryPrix)) {
                        stmtPrix.setInt(1, idProduit);
                        try (ResultSet resultSetPrix = stmtPrix.executeQuery()) {
                            if (resultSetPrix.next()) {
                                prix = resultSetPrix.getDouble("prix");
                            }
                        }
                    }

                    produits.add(new Produit(idProduit, nomProduit, idTypeProduit, prix));
                }
            }
        }

        return produits;
    }

}
