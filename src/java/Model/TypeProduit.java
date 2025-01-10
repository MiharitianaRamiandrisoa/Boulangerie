/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MIHARY
 */
public class TypeProduit {
    private int idTypeProduit;
    private String nomType;

    // Constructeur
    public TypeProduit(int idTypeProduit, String nomType) {
        this.idTypeProduit = idTypeProduit;
        this.nomType = nomType;
    }

    // Getters et Setters
    public int getIdTypeProduit() {
        return idTypeProduit;
    }

    public void setIdTypeProduit(int idTypeProduit) {
        this.idTypeProduit = idTypeProduit;
    }

    public String getNomType() {
        return nomType;
    }

    public void setNomType(String nomType) {
        this.nomType = nomType;
    }

    public static List<TypeProduit> getAllType(Connection connection) throws Exception {
        List<TypeProduit> type = new ArrayList<>();
        String query = "SELECT * FROM TypeProduit";
        if (connection == null) {
            connection = new PGConnect().getConnection();
        }
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            int idTypeProduit = rs.getInt("idTypeProduit");
            String nomProduit = rs.getString("nomType");
            type.add(new TypeProduit(idTypeProduit, nomProduit));
        }
        rs.close();
        preparedStatement.close();
        connection.close();

        return type;
    }
    
    public static String getNomById(int id, Connection con) throws Exception {
        String query = "SELECT nomType FROM typeproduit WHERE idtypeproduit = ?";
        String nomType = null;

        // Utilisation de la connexion existante ou création d'une nouvelle connexion
        con = (con != null) ? con : new PGConnect().getConnection();

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id); // Injection de l'ID dans la requête

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nomType = rs.getString("nomType"); // Récupération du résultat
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la récupération du nom du type de produit : " + e.getMessage());
        } finally {
            if (con != null) {
                con.close(); // Fermeture de la connexion si elle a été créée dans cette méthode
            }
        }

        return nomType; // Retourne le nom ou null si non trouvé
    }


    // Insertion d'un type de produit
    public static void insertType(Connection connection, String nomType) throws Exception {
        connection= (connection != null) ? connection : new PGConnect().getConnection();
    
        String query = "INSERT INTO TypeProduit (nomType) VALUES (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nomType);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    // Mise à jour d'un type de produit
    public static void updateType(Connection connection, int idTypeProduit, String nomType) throws Exception {
        connection= (connection != null) ? connection : new PGConnect().getConnection();

        String query = "UPDATE TypeProduit SET nomType = ? WHERE idTypeProduit = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nomType);
        preparedStatement.setInt(2, idTypeProduit);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }
}