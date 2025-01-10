/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.*;

/**
 *
 * @author MIHARY
 */
public class Recette {
    private int idProduit;
    private int idIngredient;
    private double qtt;

    // Constructeur
    public Recette(int idProduit, int idIngredient, double qtt) {
        this.idProduit = idProduit;
        this.idIngredient = idIngredient;
        this.qtt = qtt;
    }

    // Getters et Setters
    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public double getQtt() {
        return qtt;
    }

    public void setQtt(double qtt) {
        this.qtt = qtt;
    }

    public void insert(Connection connection) throws SQLException {
        connection = (connection != null) ? connection : new PGConnect().getConnection();

        String query = "INSERT INTO recette (idProduit, idIngredient,qtt) VALUES(?, ?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, this.idProduit);
            stmt.setInt(2, this.idIngredient);
            stmt.setDouble(3, this.qtt);
            stmt.executeUpdate();
        }
    }
}
