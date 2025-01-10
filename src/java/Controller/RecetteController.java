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

import Model.Produit;
import Model.Ingredient;
import Model.Recette;
import java.sql.SQLException;
/**
 *
 * @author MIHARY
 */
@WebServlet(name = "RecetteController", urlPatterns = {"/RecetteController"})
public class RecetteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Produit> p =Produit.getAllProduits(null);
            List<Ingredient> i= Ingredient.getAllIngredients(null);
            
            request.setAttribute("produit", p);
            request.setAttribute("ingredient", i);
            
            request.getRequestDispatcher("Insertion_recette.jsp").forward(request, response);
        
        } catch (Exception e) {
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int produit = Integer.parseInt( request.getParameter("produit"));
            int ingredient = Integer.parseInt( request.getParameter("ingredient"));
            double quantite = Double.parseDouble( request.getParameter("quantite"));

            Recette r = new Recette(produit , ingredient , quantite);
            r.insert(null);
        
        } catch (NumberFormatException | SQLException e) {
        }
        response.sendRedirect("RecetteController");
        
    }

}
