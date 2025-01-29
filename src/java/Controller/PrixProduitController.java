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
import java.sql.Date;
import Model.PrixProduit;
import Model.Produit;
import java.util.ArrayList;

/**
 *  
 * @author MIHARY
 */
@WebServlet(name = "PrixProduitController", urlPatterns = {"/PrixProduitController"})
public class PrixProduitController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
            
            String action = request.getParameter("action");
            if ("listePrix".equals(action)) {
                List<PrixProduit> pp = new ArrayList<>();
                String idProduit = request.getParameter("produit");
                if(idProduit != null && !idProduit.isEmpty()) {
                    int idProd = Integer.parseInt(idProduit);
                    pp = PrixProduit.getPrixProduitById(null , idProd);
                }else{
                    pp = PrixProduit.getAllPrixProduit(null);
                }
   
                       
                System.out.println(pp.size());

                request.setAttribute("prixProduit", pp);
                List<Produit> produits = Produit.getAllProduits(null);
                request.setAttribute("produit", produits);
                
                // Rediriger vers la vue de la liste des ingrédients
                request.getRequestDispatcher("historique_prix.jsp").forward(request, response);

            } else {
                // Afficher le formulaire d'insertion
                List<Produit> produits = Produit.getAllProduits(null);
                request.setAttribute("produit", produits);
                request.getRequestDispatcher("Insertion_prix.jsp").forward(request, response);
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
            String prixs = request.getParameter("prix");
            String dates = request.getParameter("date");
            String idProduits = request.getParameter("produit");

            // Vérification que les champs sont bien remplis
            if (prixs != null && !prixs.isEmpty() && dates != null && !dates.isEmpty()&& idProduits != null && !idProduits.isEmpty()  ) {
                double prix  = Double.parseDouble(prixs);
                Date date  = Date.valueOf(dates);
                int idProduit  = Integer.parseInt(idProduits);
                
                PrixProduit prixp = new PrixProduit(prix,date,idProduit);
                prixp.insert(null);
                // Redirection vers la liste des ingrédients après insertion
                doGet(request, response);
            } else {
                request.setAttribute("errorMessage", "Les champs ne peuvent pas être vides.");
                request.getRequestDispatcher("Insertion_ingredient.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}