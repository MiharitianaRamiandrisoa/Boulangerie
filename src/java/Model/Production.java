package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Production {

    private int idProduction;
    private Double qtt;
    private Date date;
    private int idProduit;

    // Constructeur par défaut
    public Production() {
    }

    // Constructeur avec paramètres
    public Production(Double qtt, Date date, int produit) {
        this.qtt = qtt;
        this.date = date;
        this.idProduit = produit;
    }

    // Getters et setters
    public int getIdProduction() {
        return idProduction;
    }

    public void setIdProduction(int idProduction) {
        this.idProduction = idProduction;
    }

    public Double getQtt() {
        return qtt;
    }

    public void setQtt(Double qtt) {
        this.qtt = qtt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int produit) {
        this.idProduit = produit;
    }

    // Méthode pour insérer une production
    public void insert(Connection connection) throws Exception {
        connection = (connection != null) ? connection : new PGConnect().getConnection();

        // Vérifie le stock des ingrédients avant l'insertion
        if (!checkStock(connection)) {
            throw new Exception("Impossible d'insérer la production : stock insuffisant.");
        }

        // Insertion dans la table Production
        String query = "INSERT INTO Production (qtt, _date, idProduit) VALUES (?, ?, ?) RETURNING idProduction";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDouble(1, this.qtt);
            ps.setDate(2, this.date);
            ps.setInt(3, this.idProduit);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    this.idProduction = rs.getInt("idProduction");
                }
            }
        }

        updateIngredientStock(connection);
    }

    // Méthode pour récupérer une production par ID
    public void getById(int id, Connection connection) throws Exception {
        connection = (connection != null) ? connection : new PGConnect().getConnection();

        String query = "SELECT * FROM Production WHERE idProduction = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    this.idProduction = rs.getInt("idProduction");
                    this.qtt = rs.getDouble("qtt");
                    this.date = rs.getDate("_date");
                    this.idProduit = rs.getInt("idProduit");
                } else {
                    throw new Exception("Production introuvable avec l'ID : " + id);
                }
            }
        }
        connection.close();
    }

    // Méthode pour calculer le stock disponible d'un ingrédient
    private double getStockDisponible(int idIngredient, Connection connection) throws Exception {
        boolean shouldCloseConnection = (connection == null); // Indique si on doit fermer la connexion ici
        if (connection == null) {
            connection = new PGConnect().getConnection();
        }

        String queryStock = """
                SELECT
                    SUM(CASE
                        WHEN idType = 1 THEN qtt -- Entrée de stock
                        WHEN idType = 2 THEN -qtt -- Sortie de stock
                        ELSE 0
                    END) AS stockDisponible
                FROM IngredientMvt
                WHERE idIngredient = ?
            """;

        try (PreparedStatement ps = connection.prepareStatement(queryStock)) {
            ps.setInt(1, idIngredient);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("stockDisponible");
                } else {
                    // Si aucun mouvement trouvé, le stock est à 0
                    return 0.0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log pour le débogage
            throw new Exception("Erreur lors de la récupération du stock disponible : " + e.getMessage(), e);
        } finally {
            if (shouldCloseConnection) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Log pour le débogage
                    throw new Exception("Erreur lors de la fermeture de la connexion : " + e.getMessage(), e);
                }
            }
        }
    }


    private boolean checkStock(Connection connection) throws Exception {

        connection = (connection != null) ? connection : new PGConnect().getConnection();
        String queryRecette = "SELECT idIngredient, qtt FROM Recette WHERE idProduit = ?";

        try (PreparedStatement psRecette = connection.prepareStatement(queryRecette)) {
            psRecette.setInt(1, this.idProduit);
            try (ResultSet rsRecette = psRecette.executeQuery()) {
                while (rsRecette.next()) {
                    int idIngredient = rsRecette.getInt("idIngredient");
                    double qttNecessaire = rsRecette.getDouble("qtt") * this.qtt;

                    // Calcul du stock disponible
                    double stockDisponible = getStockDisponible(idIngredient, connection);

                    if (stockDisponible < qttNecessaire) {
                        throw new Exception("Stock insuffisant pour l'ingrédient : " + Ingredient.getNomById(idIngredient,null)   +
                                ". Disponible : " + stockDisponible + ", Requis : " + qttNecessaire);
                    }
                }
            }
        }
        return true;
    }

    // Méthode pour mettre à jour les mouvements d'ingrédients après l'insertion
    // d'une production
    private void updateIngredientStock(Connection connection) throws Exception {
        connection = (connection != null) ? connection : new PGConnect().getConnection();

        // Requête pour récupérer les ingrédients nécessaires pour le produit
        String queryRecette = "SELECT idIngredient, qtt FROM Recette WHERE idProduit = ?";
        // Requête pour insérer un mouvement d'ingrédients
        String queryInsertMvt = "INSERT INTO IngredientMvt (qtt, _date, idType, idIngredient) VALUES (?, ?, ?, ?)";

        try (PreparedStatement psRecette = connection.prepareStatement(queryRecette)) {
            psRecette.setInt(1, this.idProduit);

            // Exécution de la requête pour récupérer les ingrédients nécessaires
            try (ResultSet rsRecette = psRecette.executeQuery()) {
                while (rsRecette.next()) {
                    int idIngredient = rsRecette.getInt("idIngredient");
                    double qttNecessaire = (rsRecette.getDouble("qtt") * this.qtt); // Calcul des quantités nécessaires
                    System.out.println("rsRecette.getDouble(qtt): " + rsRecette.getDouble("qtt"));
                    System.out.println("qttNecessaire: " + qttNecessaire);

                    // Préparation et insertion dans la table IngredientMvt
                    try (PreparedStatement psInsertMvt = connection.prepareStatement(queryInsertMvt)) {
                        psInsertMvt.setDouble(1, qttNecessaire); // Quantité utilisée (positive)
                        psInsertMvt.setDate(2, this.date); // Date de la production
                        psInsertMvt.setInt(3, 2); // Type de mouvement : sortie
                        psInsertMvt.setInt(4, idIngredient); // ID de l'ingrédient
                        psInsertMvt.executeUpdate();
                    }
                }
            }
            connection.close();
        }

    }

    // Méthode pour récupérer toutes les productions
    public static List<Production> getAllProductions(Connection connection) throws Exception {
        connection = (connection != null) ? connection : new PGConnect().getConnection();
        List<Production> productions = new ArrayList<>();
        String query = "SELECT * FROM Production";
        try (PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Production production = new Production();
                production.setIdProduction(rs.getInt("idProduction"));
                production.setQtt(rs.getDouble("qtt"));
                production.setDate(rs.getDate("_date"));
                production.setIdProduit(rs.getInt("idProduit"));
                productions.add(production);
            }
        }
        connection.close();
        return productions;
    }

    public static List<Production> getProductionsPaginated(Connection connection, int page, int pageSize)
            throws Exception {
        connection = (connection != null) ? connection : new PGConnect().getConnection();

        // Calcul de l'offset basé sur la page et la taille de la page
        int offset = (page - 1) * pageSize;

        List<Production> productions = new ArrayList<>();
        String query = "SELECT * FROM Production ORDER BY idProduction LIMIT ? OFFSET ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, pageSize); // Nombre de résultats à afficher par page
            ps.setInt(2, offset); // Décalage des résultats

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Production production = new Production();
                    production.setIdProduction(rs.getInt("idProduction"));
                    production.setQtt(rs.getDouble("qtt"));
                    production.setDate(rs.getDate("_date"));
                    production.setIdProduit(rs.getInt("idProduit"));
                    productions.add(production);
                }
            }
        }
        connection.close();

        return productions;
    }

    public static int getTotalProductionsCount(Connection connection) throws Exception {
        connection = (connection != null) ? connection : new PGConnect().getConnection();
        String query = "SELECT COUNT(*) AS total FROM Production";
        try (PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        connection.close();
        return 0;
    }

}