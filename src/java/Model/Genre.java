package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Genre {
    private int idGenre;
    private String genre;

    public Genre() {

    }

    public Genre(int idGenre, String genre) {
        this.idGenre = idGenre;
        this.genre = genre;
    }

    public int getIdGenre() {
        return idGenre;
    }
    public void setIdGenre(int idGenre) {
        this.idGenre = idGenre;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public static String getGenreById(Connection connection, int id) throws SQLException {
        String genre = "";
        connection = (connection != null) ? connection : new PGConnect().getConnection();
        String query = "select genre from Genre where idGenre = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    genre = rs.getString("genre");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log de l'erreur pour le débogage
        }
        return genre;
    }
}
