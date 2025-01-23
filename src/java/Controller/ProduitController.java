package Controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Model.Produit;
import Model.TypeProduit;
import java.util.List;
import Model.Ingredient;
import Model.Parfum;
import java.sql.SQLException;

@WebServlet(name = "ProduitController", urlPatterns = { "/ProduitController" })
public class ProduitController extends HttpServlet {

    private static final String page_insertion = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("listProduits".equals(action)) {
                // Récupérer les paramètres du formulaire de recherche
                String nom = request.getParameter("nom");
                Double prixMin = null;
                Double prixMax = null;
                Integer typeProduit = null;
                Integer idIngredient = null;

                try {
                    if (request.getParameter("prixMin") != null && !request.getParameter("prixMin").isEmpty()) {
                        prixMin = Double.valueOf(request.getParameter("prixMin"));
                    }
                    if (request.getParameter("prixMax") != null && !request.getParameter("prixMax").isEmpty()) {
                        prixMax = Double.valueOf(request.getParameter("prixMax"));
                    }
                    if (request.getParameter("typeProduit") != null && !request.getParameter("typeProduit").isEmpty()) {
                        typeProduit = Integer.valueOf(request.getParameter("typeProduit"));
                    }
                    if (request.getParameter("ingredient") != null && !request.getParameter("ingredient").isEmpty()) {
                        idIngredient = Integer.valueOf(request.getParameter("ingredient"));
                    }
                } catch (NumberFormatException e) {
                    // Log the error if necessary
                }

                // Récupérer les produits filtrés
                List<Produit> produits = Produit.getProduitsFiltered(nom, prixMin, prixMax, typeProduit, idIngredient,
                        null);

                // Ajouter les produits filtrés et les types de produits à la requête
                List<TypeProduit> typesProduit = TypeProduit.getAllType(null);
                List<Ingredient> ing = Ingredient.getAllIngredients(null);

                request.setAttribute("produits", produits);
                request.setAttribute("typesProduit", typesProduit);
                request.setAttribute("ingredient", ing);

                request.getRequestDispatcher("Liste_produit.jsp").forward(request, response);

            } else if ("getIngrédients".equals(action)) {
                System.out.println("ee oo");
                int produitId = Integer.parseInt(request.getParameter("produitId"));
                List<Ingredient> ingredients = Produit.getIngredientsByProduit(null, produitId);
                if (ingredients == null || ingredients.isEmpty()) {
                    System.out.println("Aucun ingrédient trouvé pour le produit avec ID " + produitId);
                } else {
                    System.out.println("Ingrédients trouvés : " + ingredients.size());
                }
                request.setAttribute("ingredients", ingredients);

                StringBuilder html = new StringBuilder();
                 
                if(ingredients.size()>0){
                    for (Ingredient ingredient : ingredients) {
                       html.append("<li>").append(ingredient.getNomIngredient()).append("</li>");
                    }
                }else{
                    html.append( "Aucun Ingredient pour ce produit" );
                }
                 
                 response.getWriter().write(html.toString());
            }

            else {
                List<TypeProduit> typesProduit = TypeProduit.getAllType(null);
                request.setAttribute("typesProduit", typesProduit);
                List<Parfum> parfums = Parfum.getAll(null);

                // Ajouter les données au modèle
                request.setAttribute("parfum", parfums);
                request.getRequestDispatcher("Insertion_produit.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Une erreur est survenue : " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String nomProduit = request.getParameter("nomProduit");
            int idTypeProduit = Integer.parseInt(request.getParameter("idTypeProduit"));
            Double prix = Double.valueOf(request.getParameter("prix"));
            int idParfum= Integer.parseInt(request.getParameter("parfum"));

            Produit produit = new Produit(nomProduit, idTypeProduit, prix,idParfum);
            produit.insert(null);
            response.sendRedirect("ProduitController?action=listProduits");

        } catch (IOException | NumberFormatException | SQLException e) {
            request.setAttribute("errorMessage", "Erreur : " + e.getMessage());
            doGet(request, response);
        }
    }
}