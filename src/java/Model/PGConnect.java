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

public class PGConnect {

    Connection c;

    public PGConnect() {
        try {
            Class.forName("org.postgresql.Driver");
            this.c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/boulangerie", "postgres", "pass");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public Connection getConnection() {
        return c;
    }

    public void setConnection(Connection c) {
        this.c = c;
    }

    public void close() throws SQLException {
        if (c != null) {
            c.close();
        }
    }

}