/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.util.List;

import Model.Ingredient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Model.Unite;
import java.sql.SQLException;

/**
 *  
 * @author MIHARY
 */
@WebServlet(name = "IngredientController", urlPatterns = {"/IngredientController"})
public class IngredientController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
            
            String action = request.getParameter("action");

            if ("Liste".equals(action)) {
                int page = 1;
                int itemsPerPage = 10;

                if (request.getParameter("page") != null) {
                    page = Integer.parseInt(request.getParameter("page"));
                }

                if (request.getParameter("itemsPerPage") != null) {
                    itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage"));
                }

                // Récupérer les ingrédients pour la page courante
                List<Ingredient> ingredients = Ingredient.getIngredientsByPage(null, page, itemsPerPage);

                // Récupérer le nombre total d'ingrédients
                int totalIngredients = Ingredient.getTotalIngredients(null);

                // Calculer le nombre total de pages
                int totalPages = (int) Math.ceil((double) totalIngredients / itemsPerPage);

                // Ajouter les données au modèle
                request.setAttribute("ingredients", ingredients);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("itemsPerPage", itemsPerPage);

                // Rediriger vers la vue de la liste des ingrédients
                request.getRequestDispatcher("Liste_ingredient.jsp").forward(request, response);

            } else {
                // Afficher le formulaire d'insertion
                List<Unite> unite = Unite.getAll(null);
                request.setAttribute("listeUnite", unite);
                request.getRequestDispatcher("Insertion_ingredient.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur : " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Récupération des paramètres du formulaire
            String nom = request.getParameter("Nom");
            String unite = request.getParameter("unite");

            // Vérification que les champs sont bien remplis
            if (nom != null && !nom.isEmpty() && unite != null && !unite.isEmpty()) {
                int idunite = Integer.parseInt(unite);

                // Création d'un objet Ingredient et insertion dans la base de données
                Ingredient ingredient = new Ingredient(nom, idunite);
                ingredient.insert(null); // Insérer dans la base de données

                // Redirection vers la liste des ingrédients après insertion
                doGet(request, response);
            } else {
                request.setAttribute("errorMessage", "Les champs ne peuvent pas être vides.");
                request.getRequestDispatcher("Insertion_ingredient.jsp").forward(request, response);
            }
        } catch (ServletException | IOException | NumberFormatException | SQLException e) {
            request.setAttribute("errorMessage", "Erreur : " + e.getMessage());
            request.getRequestDispatcher("Insertion_ingredient.jsp").forward(request, response);
        }
    }
}