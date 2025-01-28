package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrixProduit {
    int idPrixProduit;
    double prix;
    Date date;
    int idProduit;
    
    public PrixProduit() {
    }

    public PrixProduit( double prix, Date date, int idProduit) {
        this.prix = prix;
        this.date = date;
        this.idProduit = idProduit;
    }
    
    public int getIdPrixProduit() {
        return idPrixProduit;
    }
    public void setIdPrixProduit(int idPrixProduit) {
        this.idPrixProduit = idPrixProduit;
    }
    public double getPrix() {
        return prix;
    }
    public void setPrix(float prix) {
        this.prix = prix;
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
    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public void insert(Connection connection) throws SQLException  {
        System.out.println("eto");
        connection = (connection != null) ? connection : new PGConnect().getConnection();
        String query = "INSERT INTO PrixProduit(prix, _date, idProduit) VALUES(?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, this.prix);
            stmt.setDate(2, this.date);
            stmt.setInt(3, this.idProduit);
            stmt.executeUpdate();
        }
    }

    public static List<PrixProduit> getAllPrixProduit(Connection connection) throws Exception {
        connection = (connection != null) ? connection : new PGConnect().getConnection();
        List<PrixProduit> prixProduits = new ArrayList<>();
        String query = "SELECT * FROM PrixProduit order by idProduit , _date";
        try (PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PrixProduit temp = new PrixProduit();
                temp.setIdPrixProduit(rs.getInt("idPrixProduit"));
                temp.setPrix(rs.getFloat("prix"));
                temp.setDate(rs.getDate("_date"));
                temp.setIdProduit(rs.getInt("idProduit"));
                prixProduits.add(temp);
            }
        }
        connection.close();
        return prixProduits;
    }
    public static List<PrixProduit> getPrixProduitById(Connection connection, int idProduit) throws Exception {
        connection = (connection != null) ? connection : new PGConnect().getConnection();
        List<PrixProduit> prixProduits = new ArrayList<>();
        String query = "SELECT * FROM PrixProduit where idProduit = ? order by idProduit , _date ";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idProduit);

            try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                    PrixProduit temp = new PrixProduit();
                    temp.setIdPrixProduit(rs.getInt("idPrixProduit"));
                    temp.setPrix(rs.getFloat("prix"));
                    temp.setDate(rs.getDate("_date"));
                    temp.setIdProduit(rs.getInt("idProduit"));
                    prixProduits.add(temp);
                }
            }
        }
        connection.close();
        return prixProduits;
    }
}
