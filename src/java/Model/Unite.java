/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MIHARY
 */
public class Unite {

    int id;
    String nom;
    String abreviation;

    public Unite(int id, String nom, String abreviation) {
        this.id = id;
        this.nom = nom;
        this.abreviation = abreviation;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public static List<Unite> getAll(Connection connection) throws Exception {
        if (connection == null) {
            connection = new PGConnect().getConnection();
        }
        List<Unite> Unite = new ArrayList<>();
        String query = "SELECT * FROM unite";
        try (PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("idUnite");
                String nomUnite = resultSet.getString("nomUnite");
                String Abreviation = resultSet.getString("abreviation");
                Unite.add(new Unite(id, nomUnite, Abreviation));
            }
        }
        connection.close();
        return Unite;
    }

    public static String getNomById(int id, Connection con) throws Exception {
        String abreviation = null; // Valeur par défaut si l'unité n'est pas trouvée
        String query = "SELECT abreviation FROM unite WHERE idUnite = ?";

        try (Connection connection = (con != null) ? con : new PGConnect().getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id); // Remplacer le paramètre dans la requête
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    abreviation = resultSet.getString("abreviation");
                }
            }
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération du nom de l'unité : " + e.getMessage(), e);
        }

        return abreviation; // Retourner le nom ou null si non trouvé
    }
}