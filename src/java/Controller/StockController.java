/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.StockIngredient;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import Model.StockProduit;

/**
 *
 * @author MIHARY
 */
@WebServlet(name = "StockController", urlPatterns = {"/StockController"})
public class StockController extends HttpServlet {
       @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Récupérer le type de stock (produit ou ingrédient)
            String type = request.getParameter("type");
            System.out.println("type: " + type);
            if (type == null || type.isEmpty()) {
                type = "produit"; // Valeur par défaut
            }

            int page = 1;
            int pageSize = 10;

            // Récupérer les paramètres de pagination
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            if (request.getParameter("pageSize") != null) {
                pageSize = Integer.parseInt(request.getParameter("pageSize"));
            }

            if (type.equals("produit")) {
                // Récupérer les stocks de produits avec pagination
                List<StockProduit> stocks = StockProduit.getAllStocks(null, page, pageSize);

                // Récupérer le total des stocks pour calculer le nombre total de pages
                int totalStocks = StockProduit.getTotalProduitCount(null);
                int totalPages = (int) Math.ceil((double) totalStocks / pageSize);

                // Ajouter les attributs à la requête
                request.setAttribute("stocks", stocks);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                // Rediriger vers la page d'affichage des stocks de produits
                request.getRequestDispatcher("Liste_stock_produit.jsp").forward(request, response);

            } else if (type.equals("ingredient")) {
                // Récupérer les stocks d'ingrédients avec pagination
                List<StockIngredient> stocks = StockIngredient.getAllStocks(null, page, pageSize);

                // Récupérer le total des stocks pour calculer le nombre total de pages
                int totalStocks = StockIngredient.getTotalIngredientCount(null);
                int totalPages = (int) Math.ceil((double) totalStocks / pageSize);
                System.out.println("eto zao");
                // Ajouter les attributs à la requête
                request.setAttribute("stocks", stocks);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                // Rediriger vers la page d'affichage des stocks d'ingrédients
                request.getRequestDispatcher("Liste_stock_ingredient.jsp").forward(request, response);

            } else {
                // Si le type est inconnu, afficher une erreur
                throw new IllegalArgumentException("Type de stock inconnu : " + type);
            }

        } catch (Exception  e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur : " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}