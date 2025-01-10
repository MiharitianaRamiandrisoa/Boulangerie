/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Ingredient;
import Model.Parfum;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Model.Produit;
import Model.TypeProduit;
import Model.Vente;
import Model.VenteDetail;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author MIHARY
 */
@WebServlet(name = "VenteController", urlPatterns = { "/VenteController" })
public class VenteController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            if (action != null && action.equals("liste")) {
                // Récupérer les filtres depuis la requête
                String parfumParam = request.getParameter("parfum");
                String typeProduitParam = request.getParameter("typeProduit");

                Integer parfum = (parfumParam != null && !parfumParam.isEmpty()) ? Integer.valueOf(parfumParam) : null;
                Integer typeProduit = (typeProduitParam != null && !typeProduitParam.isEmpty())
                        ? Integer.valueOf(typeProduitParam)
                        : null;

                System.out.println("parfum: " + parfum);
                System.out.println("typeProduit: " + typeProduit);

                // Récupérer les ventes filtrées
                List<VenteDetail> vente = Vente.getFilteredVentes(null, parfum, typeProduit);
                List<TypeProduit> typesProduit = TypeProduit.getAllType(null);
                List<Parfum> parfums = Parfum.getAll(null);

                // Ajouter les données au modèle
                request.setAttribute("parfum", parfums);
                request.setAttribute("vente", vente);
                request.setAttribute("typesProduit", typesProduit);

                // Renvoi à la vue
                request.getRequestDispatcher("Liste_vente.jsp").forward(request, response);

            } else {
                List<Produit> produits = Produit.getAllProduits(null);
                request.setAttribute("produits", produits);
                request.getRequestDispatcher("Insertion_vente.jsp").forward(request, response);
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
            String quantiteStr = request.getParameter("qttProduit");
            String dateStr = request.getParameter("date");

            if (produitId != null && !produitId.isEmpty() && quantiteStr != null && !quantiteStr.isEmpty()) {
                int idProduit = Integer.parseInt(produitId);
                Double quantite = Double.valueOf(quantiteStr);
                Date date;
                if (dateStr != null && !dateStr.isEmpty()) {
                    date = java.sql.Date.valueOf(dateStr); // Conversion de la date string en SQL Date
                } else {
                    date = new java.sql.Date(System.currentTimeMillis()); // Date actuelle si vide ou null
                }

                // Créer une instance de Vente et insérer dans la base de données
                Vente vente = new Vente();
                vente.setDate(date); // Date actuelle
                VenteDetail vd = new VenteDetail();
                vd.setDate(date);
                vd.setIdProduit(idProduit);
                vd.setQtt(quantite);
                vente.insert(null,vd); // Insérer la vente dans la base de données

                // Rediriger vers la liste des ventes après insertion
                response.sendRedirect("VenteController");
            } else {
                request.setAttribute("errorMessage", "Tous les champs doivent être remplis.");
                request.getRequestDispatcher("Insertion_vente.jsp").forward(request, response);
            }
        }  catch (Exception e) {
            e.printStackTrace();
            try {
                // Ajouter la liste des produits en cas d'exception
                List<Produit> produits = Produit.getAllProduits(null);
                request.setAttribute("produits", produits);
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("errorMessage", "Erreur critique : " + ex.getMessage());
            }
            request.setAttribute("errorMessage", "Erreur : " + e.getMessage());
            request.getRequestDispatcher("Insertion_vente.jsp").forward(request, response);
        }
    }
}
