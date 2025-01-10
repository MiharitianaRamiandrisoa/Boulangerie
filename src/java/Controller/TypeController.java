/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.TypeProduit;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 *
 * @author MIHARY
 */
@WebServlet(name = "TypeController", urlPatterns = { "/TypeController" })
public class TypeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Insertion_type.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            String nomType = request.getParameter("nomType");

            if ("insert".equals(action)) {
                // Insertion d'un nouveau type de produit
                TypeProduit.insertType(null, nomType);
            } else if ("update".equals(action)) {
                // Mise à jour d'un type de produit existant
                int idTypeProduit = Integer.parseInt(request.getParameter("idTypeProduit"));
                TypeProduit.updateType(null, idTypeProduit, nomType);
            }
            request.getRequestDispatcher("Insertion_type.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors de l'insertion ou de la mise à jour.");
            request.getRequestDispatcher("Insertion_type.jsp").forward(request, response);
        }
    }
}