/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.sql.Date;

import Model.Produit;
import Model.Production;

/**
 *
 * @author MIHARY
 */
@WebServlet(name = "ProductionController", urlPatterns = {"/ProductionController"})
public class ProductionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try { 
            String action = request.getParameter("action");
            String pageParam = request.getParameter("page");

            int itemsPerPage = 10; // Nombre d'éléments par page
            int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;

            if (action != null && action.equals("liste")) {
                // Récupérer le nombre total de productions
                int totalProductions = Production.getTotalProductionsCount(null); // Compte total des productions
                int totalPages = (int) Math.ceil((double) totalProductions / itemsPerPage);

                // Récupérer les données paginées
                List<Production> productions = Production.getProductionsPaginated(null, currentPage, itemsPerPage);

                // Ajouter les attributs nécessaires à la requête
                request.setAttribute("productions", productions);
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("totalPages", totalPages);

                // Rediriger vers la page de la liste des productions
                request.getRequestDispatcher("Liste_production.jsp").forward(request, response);
            } else {
                // Afficher les produits pour le formulaire d'insertion
                List<Produit> produits = Produit.getAllProduits(null);
                request.setAttribute("produits", produits);

                // Rediriger vers la page du formulaire d'insertion
                request.getRequestDispatcher("Insertion_production.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur : " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Récupération des paramètres du formulaire
            String produitId = request.getParameter("produit");
            String quantiteStr = request.getParameter("quantite");
            String dateStr = request.getParameter("date"); // Récupération de la date

            // Validation des champs obligatoires
            if (produitId == null || produitId.isEmpty() || quantiteStr == null || quantiteStr.isEmpty()) {
                request.setAttribute("errorMessage", "Tous les champs doivent être remplis.");
                List<Produit> produits = Produit.getAllProduits(null); // Charger les produits pour réafficher le formulaire
                request.setAttribute("produits", produits);
                request.getRequestDispatcher("Insertion_production.jsp").forward(request, response);
                return;
            }

            // Conversion des valeurs
            int idProduit = Integer.parseInt(produitId);
            double quantite = Double.parseDouble(quantiteStr);
            Date date = (dateStr != null && !dateStr.isEmpty()) ? Date.valueOf(dateStr) : new Date(System.currentTimeMillis());
           
            // Création de l'objet Production
            Production production = new Production();
            production.setIdProduit(idProduit);
            production.setQtt(quantite);
            production.setDate(date);

            production.insert(null); // Méthode pour insérer dans la base de données

            // Redirection vers page insertion après succès
            response.sendRedirect("ProductionController");
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Les données saisies sont invalides. Veuillez vérifier vos entrées.");
            // Recharger les produits pour réafficher le formulaire
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur: " + e.getMessage());
            doGet(request, response); // Retourne au formulaire avec l'erreur
        }
    }
}