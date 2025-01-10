package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MIHARY
 */
public class StockProduit {
    private int idProduit;
    private int stockDisponible;

    // Constructeurs
    public StockProduit() {
    }

    public StockProduit(int idProduit, int stockDisponible) {
        this.idProduit = idProduit;
        this.stockDisponible = stockDisponible;
    }

    // Getters et Setters
    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    // Méthodes pour la gestion des stocks

    /**
     * Calcule la quantité totale produite pour un produit donné.
     */
    public static int getTotalProduction(int idProduit, Connection connection) throws SQLException {
        String query = "SELECT COALESCE(SUM(qtt), 0) AS totalProduction FROM Production WHERE idProduit = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idProduit);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("totalProduction");
                }
            }
        }
        return 0;
    }

    /**
     * Calcule la quantité totale vendue pour un produit donné.
     */
    public static int getTotalVentes(int idProduit, Connection connection) throws SQLException {
        String query = "SELECT COALESCE(SUM(qtt), 0) AS totalVentes FROM VenteDetail WHERE idProduit = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idProduit);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("totalVentes");
                }
            }
        }
        return 0;
    }

    public static int getStockDisponible(int idProduit, Connection connection) throws SQLException {
        int totalProduction = getTotalProduction(idProduit, connection);
        int totalVentes = getTotalVentes(idProduit, connection);
        return totalProduction - totalVentes;
    }

    /**
     * Met à jour l'attribut stockDisponible pour cette instance.
     * 
     * @param con
     * @throws java.sql.SQLException
     */
    public void updateStock(Connection connection) throws SQLException {
        this.stockDisponible = getStockDisponible(this.idProduit, connection);
    }

    public static List<StockProduit> getAllStocks(Connection connection, int page, int pageSize) throws SQLException {
        connection = (connection != null) ? connection : new PGConnect().getConnection();

        // Calculer l'offset pour la pagination
        int offset = (page - 1) * pageSize;

        // Ajouter LIMIT et OFFSET pour la pagination
        String query = "SELECT idProduit FROM Produit LIMIT ? OFFSET ?";
        List<StockProduit> stocks = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Passer les paramètres de pagination
            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    int idProduit = resultSet.getInt("idProduit");

                    // Utilisation des méthodes getTotalProduction et getTotalVentes pour calculer
                    // le stock
                    int totalProduction = getTotalProduction(idProduit, connection);
                    int totalVentes = getTotalVentes(idProduit, connection);
                    int stockDisponible = totalProduction - totalVentes; // Calcul du stock disponible

                    // Créer l'objet Stock et l'ajouter à la liste
                    StockProduit stock = new StockProduit(idProduit, stockDisponible);
                    stocks.add(stock);
                }
            }
        }
        return stocks;
    }

    public static int getTotalProduitCount(Connection connection) throws SQLException {
        connection = (connection != null) ? connection : new PGConnect().getConnection();

        String query = "SELECT COUNT(*) AS total FROM Produit";
        try (PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

}