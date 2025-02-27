
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

    public Vendeur(int idVendeur, String nom, int idgenre, double commission) {
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

    public static Map<String, Double> getCommission(Connection connection, Integer mois, Integer annee,
        double seuilVente) throws SQLException {

    connection = (connection != null) ? connection : new PGConnect().getConnection();
    Map<String, Double> commissionParGenre = new HashMap<>();

    StringBuilder query = new StringBuilder("""
                SELECT
                    g.genre AS GenreVendeur,
                    SUM(
                        CASE
                            WHEN total_ventes.totalVente >= ?
                            THEN total_ventes.totalVente * (
                                SELECT c.montant 
                                FROM Commission c 
                                WHERE c._date <= total_ventes.dateVente 
                                ORDER BY c._date DESC 
                                LIMIT 1
                            ) / 100
                            ELSE 0
                        END
                    ) AS sommeCommission
                FROM Vente v
                JOIN (
                    SELECT
                        pv.idVente,
                        SUM(pv.prix * pv.qtt) AS totalVente,
                        MAX(pv._date) AS dateVente  
                    FROM prixProduitVente pv
                    GROUP BY pv.idVente
                ) AS total_ventes ON v.idVente = total_ventes.idVente
                JOIN Vendeur ve ON v.idVendeur = ve.idVendeur
                JOIN Genre g ON ve.idGenre = g.idGenre
                WHERE 1=1
            """);

        // Ajout des conditions dynamiques
        if (mois != null) {
            query.append(" AND EXTRACT(MONTH FROM v._date) = ?");
        }
        if (annee != null) {
            query.append(" AND EXTRACT(YEAR FROM v._date) = ?");
        }

        query.append(" GROUP BY g.genre");

        try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
            int paramIndex = 1;
            preparedStatement.setDouble(paramIndex++, seuilVente); // Seuil

            if (mois != null) {
                preparedStatement.setInt(paramIndex++, mois);
            }
            if (annee != null) {
                preparedStatement.setInt(paramIndex++, annee);
            }

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
