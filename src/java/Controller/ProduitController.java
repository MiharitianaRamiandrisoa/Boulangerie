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
//               request.getRequestDispatcher("Selection_conseil.jsp").forward(request, response);
                
            }else if ("viewConseilMois".equals(action)){
                // Ajouter la logique pour récupérer les produits du conseil du mois
                String moisParam = request.getParameter("mois");
                String anneeParam = request.getParameter("annee");
                // Initialiser les valeurs par défaut si les paramètres sont manquants ou invalides
                int mois = (moisParam != null && !moisParam.isEmpty()) ? Integer.parseInt(moisParam) : -1;
                int annee = (anneeParam != null && !anneeParam.isEmpty()) ? Integer.parseInt(anneeParam) : 2025 ;

                System.out.println(mois);
                System.out.println(annee);
                List<Produit> conseils = new Produit().getConseil(mois, annee,  null);
                System.out.println(mois);
                System.out.println(annee);
                request.setAttribute("produits", conseils);
                request.getRequestDispatcher("Liste_produit.jsp").forward(request, response);
            }
            else if ("getIngrédients".equals(action)) {
                int produitId = Integer.parseInt(request.getParameter("produitId"));
                List<Ingredient> ingredients = Produit.getIngredientsByProduit(null, produitId);

                StringBuilder html = new StringBuilder();
                if (ingredients != null && !ingredients.isEmpty()) {
                    for (Ingredient ingredient : ingredients) {
                        html.append("<li>").append(ingredient.getNomIngredient()).append("</li>");
                    }
                } else {
                    html.append("Aucun ingrédient pour ce produit.");
                }

                response.setContentType("text/html");
                response.getWriter().write(html.toString());
                return; // Arrêtez l'exécution pour éviter d'autres actions
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
                  String action = request.getParameter("action");
            if ("addConseilMois".equals(action)) {
                Integer produitId = Integer.parseInt(request.getParameter("produitId"));

                if (produitId != null) {
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
                    System.out.println(mois);
                    System.out.println(annee);
                    Produit.insertConseil(produitId, mois, annee, null);
                    request.setAttribute("message", "Produit ajouté au Conseil du Mois avec succès !");
                } else {
                    request.setAttribute("error", "Produit introuvable !");
                }

                // Envoyer vers la liste des produits
                request.getRequestDispatcher("ProduitController?action=listProduits").forward(request, response);
                return;
            }else{
                String nomProduit = request.getParameter("nomProduit");
                int idTypeProduit = Integer.parseInt(request.getParameter("idTypeProduit"));
                Double prix = Double.valueOf(request.getParameter("prix"));
                int idParfum= Integer.parseInt(request.getParameter("parfum"));

                Produit produit = new Produit(nomProduit, idTypeProduit, prix,idParfum);
                produit.insert(null);
                response.sendRedirect("ProduitController?action=listProduits");
            }

        } catch (IOException | NumberFormatException | SQLException e) {
            request.setAttribute("errorMessage", "Erreur : " + e.getMessage());
            doGet(request, response);
        }
    }
}