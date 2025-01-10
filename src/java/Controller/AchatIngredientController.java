/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.IngredientMvt;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Model.IngredientMvt;
import Model.Ingredient;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author MIHARY
 */
@WebServlet(name = "AchatIngredientController", urlPatterns = {"/AchatIngredientController"})
public class AchatIngredientController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Ingredient> i= Ingredient.getAllIngredients(null);
            request.setAttribute("ingredient", i);

            request.getRequestDispatcher("Achat_ingredient.jsp").forward(request, response);
        
            
        } catch (Exception e) {
        }
        
    }
            

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer les données du formulaire
            int idIngredient = Integer.parseInt(request.getParameter("ingredient"));
            int quantite = Integer.parseInt(request.getParameter("quantite"));
            String dateStr = request.getParameter("date");
            
            Date date = (dateStr != null && !dateStr.isEmpty()) ?  Date.valueOf(dateStr) : new Date(System.currentTimeMillis()) ; 

            // Type de mouvement toujours "Entrée"
            int idType = 1;

            // Créer un objet de type IngredientMvt
            IngredientMvt mvt = new IngredientMvt();
            mvt.setIdIngredient(idIngredient);
            mvt.setQtt(quantite);
            mvt.setDate(date);
            mvt.setIdType(idType);

            // Insérer dans la base de données
            mvt.insert(null); // Cette méthode doit être définie dans la classe IngredientMvt

            // Rediriger vers une page de confirmation ou de liste
            doGet(request,response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de l'insertion : " + e.getMessage());
            doGet(request, response);
        }
    }
}
