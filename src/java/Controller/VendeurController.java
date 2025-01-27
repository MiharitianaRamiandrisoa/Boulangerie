/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import Model.Vendeur;
import java.util.List;
import java.util.Map;
/**
 *
 * @author MIHARY
 */

@WebServlet(name = "VendeurController", urlPatterns = {"/VendeurController"})
public class VendeurController extends HttpServlet {
    
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        String moisString = request.getParameter("mois");
        String anneeString = request.getParameter("annee");
        String seuilVenteString = request.getParameter("seuilVente");

        int mois = (moisString != null && !moisString.equals("")) ? Integer.parseInt(moisString) : 1;
        int annee = (anneeString != null && !anneeString.equals("")) ? Integer.parseInt(anneeString) : 2025;
        double seuilVente = (seuilVenteString != null && !seuilVenteString.equals("")) ? Double.parseDouble(seuilVenteString) : 7.0;  // Valeur par défaut 7.0
        
        System.out.println("Mois : " + mois);
        System.out.println("Année : " + annee);
        System.out.println("Seuil des ventes : " + seuilVente);
        
        try {
            // Appel à la méthode getCommission modifiée
            Map<String, Double> commissionsParGenre = Vendeur.getCommission(null, mois, annee, seuilVente);
            
            // Passer la Map contenant les commissions par genre à la JSP
            request.setAttribute("commissions", commissionsParGenre);
            request.getRequestDispatcher("Liste_vendeur.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}