
package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Vendeur {
    private int idVendeur;
    private String nom;
    private double commmission;
    private int idGenre;

    
    
    public Vendeur() {
    }
    public Vendeur(int idVendeur, String nom) {
        this.idVendeur = idVendeur;
        this.nom = nom;
    }
    public Vendeur(int idVendeur, String nom,int idgenre, double commission   ) {
        this.idVendeur = idVendeur;
        this.nom = nom;
        this.commmission = commission;
        this.idGenre = idgenre;
    }

    public String getNom() {
        return nom;
    }
    
    public int getIdGenre() {
        return this.idGenre;
    }
    public int getIdVendeur() {
        return idVendeur;
    }

    public void setIdVendeur(int idVendeur) {
        this.idVendeur = idVendeur;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public double getCom() {
        return commmission;
    }
    public void setCommmission(double commmission) {
        this.commmission = commmission;
    }
    
    
    public static List<Vendeur> getAllVendeur(Connection connection) throws SQLException {
        
        List<Vendeur> vendeur = new ArrayList<>();
        String query = "SELECT * FROM vendeur";
        connection = (connection != null) ? connection : new PGConnect().getConnection();
        
        try (
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("idVendeur");
                String nom = rs.getString("nom");

                vendeur.add(new Vendeur(id, nom));
            }
        }
        connection.close();
        return vendeur;
    }
    
    public static String getNomVendeurById(Connection connection, int id) throws SQLException {
        connection = (connection != null) ? connection : new PGConnect().getConnection();
        String query = "SELECT nom FROM Vendeur WHERE idVendeur = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("nom");
                }
            }
        }
        return null; // Retourne null si aucun vendeur n'a été trouvé
    }
    

    public static Map<String, Double> getCommission(Connection connection, Integer mois, Integer annee, double seuilVente) throws SQLException {
        connection = (connection != null) ? connection : new PGConnect().getConnection();
        Map<String, Double> commissionParGenre = new HashMap<>();

        if (mois == null) {
            mois = 1;
        }
        if (annee == null) {
            annee = 2025;
        }

        String query = """
            SELECT 
                g.genre AS GenreVendeur,
                SUM(
                    CASE 
                        WHEN total_ventes.totalVente >= ? 
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
            WHERE EXTRACT(MONTH FROM v._date) = ? AND EXTRACT(YEAR FROM v._date) = ?
            GROUP BY g.genre;
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, seuilVente);  // Seuil pour le total des ventes
            preparedStatement.setInt(2, mois);            // Mois
            preparedStatement.setInt(3, annee);           // Année

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String genreVendeur = resultSet.getString("GenreVendeur");
                    double sommeCommission = resultSet.getDouble("sommeCommission");

                    // Ajouter la commission au genre correspondant dans la map
                    commissionParGenre.put(genreVendeur, sommeCommission);
                }
            }
        }

        return commissionParGenre;
    }

}
