/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;

    /**
     *
     * @author MIHARY
     */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un mouvement d'ingrédient.
 */
public class IngredientMvt {
    private int idMvt;
    private int qtt;
    private Date date;
    private int idType; // 1 = Entrée, 2 = Sortie
    private int idIngredient;

    // Constructeur vide
    public IngredientMvt() {
    }

    // Constructeur complet
    public IngredientMvt(int idMvt, int qtt, Date date, int idType, int idIngredient) {
        this.idMvt = idMvt;
        this.qtt = qtt;
        this.date = date;
        this.idType = idType;
        this.idIngredient = idIngredient;
    }

    // Getters et Setters
    public int getIdMvt() {
        return idMvt;
    }

    public void setIdMvt(int idMvt) {
        this.idMvt = idMvt;
    }

    public int getQtt() {
        return qtt;
    }

    public void setQtt(int qtt) {
        this.qtt = qtt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    /**
     * Insère un mouvement d'ingrédient dans la base de données.
     */
    public void insert(Connection connection) throws SQLException {
        connection=  (connection != null) ? connection : new PGConnect().getConnection();
        String query = "INSERT INTO IngredientMvt (qtt, _date, idType, idIngredient) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, this.qtt);
            ps.setDate(2, this.date);
            ps.setInt(3, this.idType);
            ps.setInt(4, this.idIngredient);
            ps.executeUpdate();
        }
        if(connection ==null){
        } else {
            connection.close();
        }
    }

    /**
     * Récupère la liste de tous les mouvements d'ingrédients.
     */
    public static List<IngredientMvt> getAllMovements(Connection connection) throws SQLException {
        String query = "SELECT * FROM IngredientMvt";
        List<IngredientMvt> movements = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                movements.add(new IngredientMvt(
                        rs.getInt("idMvt"),
                        rs.getInt("qtt"),
                        rs.getDate("_date"),
                        rs.getInt("idType"),
                        rs.getInt("idIngredient")
                ));
            }
        }
        return movements;
    }

    /**
     * Récupère le stock disponible pour un ingrédient donné.
     */
    public static double getStockDisponible(int idIngredient, Connection connection) throws SQLException {
        String query = """
                SELECT 
                    SUM(CASE 
                        WHEN idType = 1 THEN qtt -- Entrée
                        WHEN idType = 2 THEN -qtt -- Sortie
                        ELSE 0 
                    END) AS stockDisponible
                FROM IngredientMvt
                WHERE idIngredient = ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idIngredient);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("stockDisponible");
                }
            }
        }
        return 0.0; // Aucun mouvement trouvé
    }

    /**
     * Supprime un mouvement d'ingrédient par son ID.
     */
    public static void deleteById(int idMvt, Connection connection) throws SQLException {
        String query = "DELETE FROM IngredientMvt WHERE idMvt = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idMvt);
            ps.executeUpdate();
        }
    }

    /**
     * Met à jour un mouvement d'ingrédient.
     */
    public void update(Connection connection) throws SQLException {
        String query = "UPDATE IngredientMvt SET qtt = ?, _date = ?, idType = ?, idIngredient = ? WHERE idMvt = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, this.qtt);
            ps.setDate(2, this.date);
            ps.setInt(3, this.idType);
            ps.setInt(4, this.idIngredient);
            ps.setInt(5, this.idMvt);
            ps.executeUpdate();
        }
    }
}
