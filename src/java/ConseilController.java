/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import Model.Produit;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MIHARY
 */
@WebServlet(urlPatterns = {"/ConseilController"})
public class ConseilController extends HttpServlet {
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    
 @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Récupérer les produits sélectionnés
        String[] selectedProduits = request.getParameterValues("selectedProduits");

        // Vérifier si des produits ont été sélectionnés
        if (selectedProduits == null || selectedProduits.length == 0) {
            // Aucun produit sélectionné
            request.setAttribute("message", "Aucun produit sélectionné. Veuillez réessayer.");
            request.getRequestDispatcher("Selection_conseil.jsp").forward(request, response);
            return;
        }
        

        // Récupérer les paramètres mois et année depuis la requête
        String moisParam = request.getParameter("mois");
        String anneeParam = request.getParameter("annee");

        // Initialiser les valeurs avec validation
        int mois = (moisParam != null && !moisParam.isEmpty()) ? Integer.parseInt(moisParam) : 1;
        int annee = (anneeParam != null && !anneeParam.isEmpty()) ? Integer.parseInt(anneeParam) : 2025;
        
        // Vérification des limites pour le mois (1 à 12)
        if (mois < 1 || mois > 12) {
            request.setAttribute("error", "Le mois doit être compris entre 1 et 12 !");
            request.getRequestDispatcher("ProduitController?action=listProduits").forward(request, response);
            return;
        }
        
        try {
            for (String produitId : selectedProduits) {
                int id = Integer.valueOf(produitId);
                Produit.insertConseil( id , mois, annee, null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Convertir les IDs des produits en une liste d'entiers

        // Redirection ou affichage d'un message de confirmation
        request.setAttribute("message", "Les produits sélectionnés ont été enregistrés avec succès !");
        request.getRequestDispatcher("Selection_conseil.jsp").forward(request, response);
    }
   

}
