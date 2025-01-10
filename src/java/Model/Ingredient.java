/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class Ingredient {
    private int idIngredient;
    private String nomIngredient;
    private int idUnite;

    // Constructeur vide
    public Ingredient() {
    }

    // Constructeur avec paramètres
    public Ingredient(int idIngredient, String nomIngredient, int unite) {
        this.idIngredient = idIngredient;
        this.nomIngredient = nomIngredient;
        this.idUnite = unite;
    }
    

    // Constructeur avec paramètres
    public Ingredient (String nomIngredient, int unite) {
        this.nomIngredient = nomIngredient;
        this.idUnite = unite;    
    }
    

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

    public int getUnite() {
        return idUnite;
    }

    public void setUnite(int unite) {
        this.idUnite = unite;
    }
    
    public void insert(Connection connection) throws SQLException  {
        connection = (connection != null) ? connection : new PGConnect().getConnection();
        String query = "INSERT INTO Ingredient(nomIngredient, idunite) VALUES(?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, this.nomIngredient);
            stmt.setInt(2, this.idUnite);
            stmt.executeUpdate();
        }
    }

    public static List<Ingredient> getAllIngredients(Connection connection) throws SQLException {
        
        List<Ingredient> ingredients = new ArrayList<>();
        String query = "SELECT * FROM ingredient";
        connection = (connection != null) ? connection : new PGConnect().getConnection();
        
        try (
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("idingredient");
                String nom = rs.getString("nomingredient");
                int idUnite = rs.getInt("idunite");

                ingredients.add(new Ingredient(id, nom , idUnite ));
            }
        }
        connection.close();
        return ingredients;
    }
    
    public static List<Ingredient> getIngredientsByPage(Connection connection, int page, int itemsPerPage) throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        int offset = (page - 1) * itemsPerPage;

        String query = "SELECT * FROM Ingredient ORDER BY idIngredient LIMIT ? OFFSET ?";
        connection = (connection != null) ? connection : new PGConnect().getConnection();
            
        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, itemsPerPage);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("idIngredient");
                    String nom = rs.getString("nomIngredient");
                    int idUnite = rs.getInt("idUnite");

                    ingredients.add(new Ingredient(id, nom, idUnite));
                }
            }
        }
        connection.close();

        return ingredients;
    }

    // Méthode pour récupérer le nombre total d'ingrédients
    public static int getTotalIngredients(Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) FROM Ingredient";

        connection= (connection != null) ? connection : new PGConnect().getConnection();
        try (
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        connection.close();
        return 0;
    }
    
    public static String getNomById(int idIngredient, Connection connection) throws SQLException {
        connection = (connection != null) ? connection : new PGConnect().getConnection();
        String query = "SELECT nomIngredient FROM ingredient WHERE idingredient = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idIngredient);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("nomIngredient");
                }
            }
        }
        return "Produit inconnu";
    }
}