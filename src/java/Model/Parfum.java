/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author MIHARY
 */
public class Parfum {

    private int idParfum;
    private String nom;

    // Constructeur
    public Parfum(int idParfum, String nom) {
        this.idParfum = idParfum;
        this.nom = nom;
    }

    // Getters et Setters
    public int getIdParfum() {
        return idParfum;
    }

    public void setIdParfum(int idParfum) {
        this.idParfum = idParfum;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Méthode pour afficher les informations de Parfum
    @Override
    public String toString() {
        return "Parfum{" +
                "idParfum=" + idParfum +
                ", nom='" + nom + '\'' +
                '}';
    }
    
    public static List<Parfum> getAll(Connection con) throws Exception {
        // Si la connexion est null, créer une nouvelle connexion
        con = (con != null) ? con : new PGConnect().getConnection();
        List<Parfum> p = new ArrayList<>();

        // Requête SQL pour récupérer tous les parfums
        String query = "SELECT * FROM parfum";

        // Exécution de la requête et traitement du ResultSet
        try (PreparedStatement stm = con.prepareStatement(query);
             ResultSet rs = stm.executeQuery()) {

            // Parcourir les résultats et créer des objets Parfum
            while (rs.next()) {
                // Récupérer les valeurs de la base de données
                int idParfum = rs.getInt("idParfum");
                String nom = rs.getString("nom");

                // Créer un objet Parfum et l'ajouter à la liste
                p.add(new Parfum(idParfum, nom));
            }
        } catch (SQLException e) {
            // Gérer les exceptions
            throw new Exception("Erreur lors de la récupération des parfums", e);
        }

        // Retourner la liste des parfums
        return p;
    }

}
