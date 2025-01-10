package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VenteDetail {
    private Date date;
    private int idProduit;
    private int idVente;
    private int idType;
    private Double qtt; // Quantité vendue

    
     public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
    
     public void setIdType(int idtype) {
        this.idType = idtype;
    }

    public int getIdType() {
        return idType;
    }
    

    // Constructeur vide
    public VenteDetail() {
    }

    // Constructeur avec paramètres
    public VenteDetail( Date date, int idProduit, int idVente, Double qtt) {
        this.idProduit = idProduit;
        this.idVente = idVente;
        this.qtt = qtt;
        this.date = date;
    }

    // Getters et Setters
    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getIdVente() {
        return idVente;
    }

    public void setIdVente(int idVente) {
        this.idVente = idVente;
    }

    public Double getQtt() {
        return qtt;
    }

    public void setQtt(Double qtt) {
        this.qtt = qtt;
    }

    // Méthode d'insertion
    public void insert(Connection connection) throws SQLException {
        // Vérifier la disponibilité du stock
        int stockDisponible = StockProduit.getStockDisponible(this.idProduit, connection);
        if (this.qtt > stockDisponible) {
            throw new SQLException("Stock insuffisant pour le produit avec ID: " + this.idProduit);
        }

        // Insérer les détails de la vente
        String query = "INSERT INTO VenteDetail (idProduit, idVente, qtt) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, this.idProduit);
            stmt.setInt(2, this.idVente);
            stmt.setDouble(3, this.qtt);
            stmt.executeUpdate();
        }
    }

    // Récupérer tous les détails de vente
    public static List<VenteDetail> getAll(Connection connection) throws SQLException {
        String query = "SELECT * FROM VenteDetail";
        List<VenteDetail> details = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                VenteDetail detail = new VenteDetail();
                detail.setIdProduit(rs.getInt("idProduit"));
                detail.setIdVente(rs.getInt("idVente"));
                detail.setQtt(rs.getDouble("qtt"));
                details.add(detail);
            }
        }
        return details;
    }
}