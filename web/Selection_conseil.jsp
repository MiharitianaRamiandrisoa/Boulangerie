<%-- 
    Document   : Selection_conseil
    Created on : 14 janv. 2025, 14:26:01
    Author     : MIHARY
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.Produit" %>
<%@ page import="Model.TypeProduit" %>
<%@ page import="Model.Ingredient" %>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="partials/_head.jsp" />

<body>
  <div class="container-scroller">
    <jsp:include page="partials/_navbar.jsp" />
    
    <div class="container-fluid page-body-wrapper">
      <jsp:include page="partials/_sidebar.jsp" />
  
      <div class="main-panel">
        <div class="content-wrapper">
          <!-- main -->
            <div class="offset-1 col-md-10 grid-margin stretch-card">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Liste des Produits</h4>

                        <!-- Formulaire de recherche -->
                        <form method="POST" action="ConseilController"> 
                            <div class="form-row">
                                <!-- Champ pour la date du conseil -->
                                <div class="col-md-3">
                                    <label for="mois">Mois</label>
                                    <select id="mois" name="mois" class="form-control">
                                        <option value="">Sélectionner un mois</option>
                                        <%
                                            String[] mois = { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre" };
                                            for (int i = 1; i <= 12; i++) {
                                        %>
                                        <option value="<%= i %>"><%= mois[i - 1] %></option>
                                        <% } %>
                                    </select>
                                </div>
                                    
                                <!-- Champ pour l'année -->
                                <div class="col-md-3">
                                    <label for="annee">Année</label>
                                    <input type="number" id="annee" name="annee" class="form-control" placeholder="Année">
                                </div>
                            </div>

                            <div class="table-responsive mt-4">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Id</th>
                                            <th>Nom</th>
                                            <th>Prix</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            // Récupérer la liste des produits filtrés depuis l'attribut "produits"
                                            List<Produit> produits = (List<Produit>) request.getAttribute("produits");
                                            if (produits != null && !produits.isEmpty()) {
                                                for (Produit produit : produits) {
                                        %>
                                            <tr>
                                                <td><%= produit.getIdProduit() %></td>
                                                <td><%= produit.getNomProduit() %></td>
                                                <td><%= produit.getPrix() %> Ar </td>
                                                <td>
                                                    <!-- Checkbox pour sélectionner le produit -->
                                                    <input 
                                                        type="checkbox" 
                                                        name="selectedProduits" 
                                                        value="<%= produit.getIdProduit() %>">
                                                </td>
                                            </tr>
                                        <%
                                                }
                                            } else {
                                        %>
                                            <tr>
                                                <td colspan="4" class="text-center">Aucun produit trouvé.</td>
                                            </tr>
                                        <%
                                            }
                                        %>
                                    </tbody>
                                </table>
                            </div>

                            <!-- Bouton pour envoyer les données -->
                            <div class="mt-3">
                                <button type="submit" class="btn btn-primary">Envoyer</button>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="partials/_footer.jsp" />
      </div>
    </div>   
  </div>
      
      
     
  <!-- plugins:js -->
  <script src="vendors/js/vendor.bundle.base.js"></script>
  <script src="vendors/chart.js/Chart.min.js"></script>
  <script src="vendors/datatables.net/jquery.dataTables.js"></script>
  <script src="vendors/datatables.net-bs4/dataTables.bootstrap4.js"></script>
  <script src="js/dataTables.select.min.js"></script>
  <script src="js/off-canvas.js"></script>
  <script src="js/hoverable-collapse.js"></script>
  <script src="js/template.js"></script>
  <script src="js/settings.js"></script>
  <script src="js/todolist.js"></script>
  <script src="js/dashboard.js"></script>
  <script src="js/Chart.roundedBarCharts.js"></script>
</body>
</html>
