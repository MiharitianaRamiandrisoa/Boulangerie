package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private int idClient;
    private String nom;

    
    public Client() {
    }

    public Client(int idClient, String nom) {
        this.idClient = idClient;
        this.nom = nom;
    }

    public int getIdClient() {
        return idClient;
    }
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }


    public static void insertClient(Connection connection, String nom) throws SQLException {
    // Vérifier ou initialiser la connexion
    connection = (connection != null) ? connection : new PGConnect().getConnection();

    // Construire une date SQL à partir du mois et de l'année
    String sql = "INSERT INTO Client (nom) VALUES (?)";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, nom);
        // Exécuter l'insertion
        stmt.executeUpdate();
    }
    }

    public String getNomClientById(Connection connection, int id) throws SQLException {
        String nom = "";
        connection = (connection != null) ? connection : new PGConnect().getConnection();
        String query = "select nom from Client where idClient = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    nom = rs.getString("nom");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log de l'erreur pour le débogage
        }
        return nom;
    }

    public static List<Client> getAllClient(Connection connection) throws Exception {
        connection = (connection != null) ? connection : new PGConnect().getConnection();

        List<Client> client = new ArrayList<>();
        String query = "SELECT * FROM Client";
        try (PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                int idClient = resultSet.getInt("idClient");
                String nom = resultSet.getString("nom");

                Client c = new Client();
                c.setIdClient(idClient);
                c.setNom(nom);
                client.add(c);
            }
        }
        return client;
    }
    
}
