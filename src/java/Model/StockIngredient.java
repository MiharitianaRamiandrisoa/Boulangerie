/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MIHARY
 */
public class StockIngredient {
    private int idIngredient;
    private String nomIngredient;
    private Double qttEnStock;

    // Constructeurs
    public StockIngredient(int idIngredient, String nomIngredient, Double qttEnStock) {
        this.idIngredient = idIngredient;
        this.nomIngredient = nomIngredient;
        this.qttEnStock = qttEnStock;
    }

    public StockIngredient() {}

    // Getters et Setters
    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getNomIngredient() {
        return nomIngredient;
    }

    public void setNomIngredient(String nomIngredient) {
        this.nomIngredient = nomIngredient;
    }

    public Double getQttEnStock() {
        return qttEnStock;
    }

    public void setQttEnStock(Double qttEnStock) {
        this.qttEnStock = qttEnStock;
    }

    // Récupérer tous les stocks d'ingrédients avec pagination
    public static List<StockIngredient> getAllStocks(Connection con, int page, int pageSize) throws Exception {
        con = (con!= null) ? con : new PGConnect().getConnection();
        List<StockIngredient> stocks = new ArrayList<>();
        String query = """
            SELECT 
                i.idIngredient, 
                i.nomIngredient, 
                SUM(CASE 
                    WHEN m.idType = 1 THEN m.qtt 
                    WHEN m.idType = 2 THEN -m.qtt 
                    ELSE 0 
                END) AS qttEnStock
            FROM Ingredient i
            LEFT JOIN IngredientMvt m ON i.idIngredient = m.idIngredient
            GROUP BY i.idIngredient, i.nomIngredient
            ORDER BY i.nomIngredient asc
            LIMIT ? OFFSET ?
        """;

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, (page - 1) * pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    stocks.add(new StockIngredient(
                        rs.getInt("idIngredient"),
                        rs.getString("nomIngredient"),
                        rs.getDouble("qttEnStock")
                    ));
                }
            }
        }
        con.close();

        return stocks;
    }

    // Compter le nombre total d'ingrédients
    public static int getTotalIngredientCount(Connection con) throws Exception {
        con = (con != null) ? con : new PGConnect().getConnection();
        String query = "SELECT COUNT(*) AS total FROM Ingredient";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        con.close();
        return 0;
    }
}