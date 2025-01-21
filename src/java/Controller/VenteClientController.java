/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Produit;
import Model.Vente;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author MIHARY
 */
@WebServlet(name = "VenteClientController", urlPatterns = {"/VenteClientController"})
public class VenteClientController extends HttpServlet {



@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            if (action != null && action.equals("venteClient")) {
//                 Récupérer les filtres depuis la requête
                Date date = null;
                if(request.getParameter("date") != null){
                    date = Date.valueOf(request.getParameter("date"));
                }

                List<Vente> vente = Vente.getClientByDateVente(null,date);
                request.setAttribute("vente", vente);
                
                // Renvoi à la vue
                request.getRequestDispatcher("ListeClientVente.jsp").forward(request, response);

            } else {
                List<Produit> produits = Produit.getAllProduits(null);
                request.setAttribute("produits", produits);
                request.getRequestDispatcher("Insertion_vente.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            PrintWriter out = response.getWriter();
            out.println(e.getMessage());
            
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
