package Model;

import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Vente {
     private int idVente;
    private Date date;
    private int client;

    // Constructeur avec paramètres
    public Vente(int idVente, Date date, int client) {
        this.idVente = idVente;
        this.date = date;
        this.client = client;
    }

    public Vente() {

    }

    // Getters et Setters
    public int getIdVente() {
        return idVente;
    }

    public void setIdVente(int idVente) {
        this.idVente = idVente;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }
    // Méthode d'insertion
     public void insert(Connection connection, VenteDetail venteDetail) throws SQLException {
        try {
            connection = (connection!= null) ? connection : new PGConnect().getConnection();
            // Début de transaction
            connection.setAutoCommit(false);

            // Insérer la vente
            String query = "INSERT INTO Vente (_date, idClient) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setDate(1, new java.sql.Date(this.date.getTime()));
                statement.setInt(2, this.getClient());
                statement.executeUpdate();

                // Récupérer l'ID généré pour la vente
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    this.idVente = rs.getInt(1);
                }
            }

            // Insérer les détails de la vente
            venteDetail.setIdVente(this.idVente);
            venteDetail.insert(connection);

            // Commit de la transaction
            connection.commit();
        } catch (SQLException e) {
            // Rollback en cas d'erreur
            connection.rollback();
            throw e;
        }
    }

    // Récupérer les ventes avec filtrage par parfum et type de produit
    public static List<VenteDetail> getFilteredVentes(Connection connection, Integer parfum, Integer type)
            throws SQLException {
        List<VenteDetail> ventes = new ArrayList<>();

        StringBuilder query = new StringBuilder(
                "SELECT v.idVente, p.idProduit produit, v._date, idParfum parfum, idTypeProduit type, qtt " +
                        "FROM VenteDetail vd " +
                        "JOIN Produit p ON vd.idProduit = p.idProduit " +
                        "join vente v on vd.idvente = v.idvente " +
                        "WHERE 1=1 ");

        // Ajout des filtres si spécifiés
        if (parfum != null) {
            query.append("AND idParfum = ? ");
        }
        if (type != null) {
            query.append("AND idTypeProduit = ? ");
        }

        connection = (connection != null) ? connection : new PGConnect().getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int paramIndex = 1;

            // Ajout des paramètres au PreparedStatement
            if (parfum != null) {
                statement.setInt(paramIndex++, parfum);
            }
            if (type != null) {
                statement.setInt(paramIndex++, type);
            }

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    VenteDetail vente = new VenteDetail();
                    vente.setIdVente(rs.getInt("idVente"));
                    vente.setIdProduit(rs.getInt("produit"));
                    vente.setIdType(rs.getInt("type"));
                    vente.setDate(rs.getDate("_date"));
                    vente.setQtt(rs.getDouble("qtt"));
                    ventes.add(vente);
                }
            }
        }
        connection.close();
        return ventes;
    }

    
     public static List<Vente> getClientByDateVente(Connection connection, Date d) {
        if( d == null){
         d= new Date(System.currentTimeMillis());
        }
        List<Vente> vente = new ArrayList<>();
        connection = (connection != null) ? connection : new PGConnect().getConnection();
        String query = "select * from Vente where _date = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Définir le paramètre idVente dans la requête
            stmt.setDate(1, d);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Récupérer les détails de la vente
                    int idVente = rs.getInt("idVente");
                    Date date = rs.getDate("_date");
                    int idClient = rs.getInt("idClient");

                    Vente v = new Vente();
                    v.setIdVente(idVente);
                    v.setDate(date);
                    v.setClient(idClient);
                    // Ajouter le détail à la liste
                    vente.add(v);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log de l'erreur pour le débogage
        }
        return vente;
    }
    
    // Récupérer une vente par son ID
    public void getById(int idVente, Connection connection) throws SQLException {
        String query = "SELECT * FROM Vente WHERE idVente = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idVente);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                this.setIdVente(rs.getInt("idVente"));
                this.setDate(rs.getDate("_date"));
            }
        }
    }

    public List<VenteDetail> getDetail(Connection connection) {
        List<VenteDetail> venteDetail = new ArrayList<>();
        connection = (connection != null) ? connection : new PGConnect().getConnection();
        String query = "select * from ventedetail where idvente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Définir le paramètre idVente dans la requête
            stmt.setInt(1, this.idVente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Récupérer les détails de la vente
                    int idProduit = rs.getInt("idProduit");
                    double qtt = rs.getDouble("qtt");

                    VenteDetail v = new VenteDetail();
                    v.setIdProduit(idProduit);
                    v.setIdVente(idVente);
                    v.setQtt(qtt);
                    // Ajouter le détail à la liste
                    venteDetail.add(v);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log de l'erreur pour le débogage
        }
        return venteDetail;

    }

}