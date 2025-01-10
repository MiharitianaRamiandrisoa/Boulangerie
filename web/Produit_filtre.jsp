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
                        <form method="GET" action="ProduitController">
                            <div class="form-row">
                                <!-- Champ pour le nom du produit -->
                                

                                <!-- Champ pour le type de produit -->
                                <div class="col-md-3">
                                    <label for="typeProduit">Type de produit</label>
                                    <select id="typeProduit" name="typeProduit" class="form-control">
                                        <option value="">Sélectionner un type</option>
                                        <%
                                            // Récupérer la liste des types de produits
                                            List<Produit> produit = (List<Produit>) request.getAttribute("produit");
                                            if (produit != null) {
                                                for (Produit p : produit) {
                                        %>
                                        <option value="<%= p.getIdProduit() %>"><%= p.getIdProduit() %></option>
                                        <%
                                                }
                                            }
                                        %>
                                    </select>
                                </div>

                                <!-- Champ pour le type de produit -->
                                <div class="col-md-3">
                                    <label for="typeProduit">Ingredient</label>
                                    <select id="typeProduit" name="ingredient" class="form-control">
                                        <option value="">Sélectionner un ingredient</option>
                                        <%
                                            // Récupérer la liste des types de Ingredient
                                            List<Ingredient> ingredient = (List<Ingredient>) request.getAttribute("ingredient");
                                            if (ingredient != null) {
                                                for (Ingredient i : ingredient) {
                                        %>
                                        <option value="<%= i.getIdIngredient() %>"><%=i.getNomIngredient() %></option>
                                        <%
                                                }
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>

                            <div class="form-row mt-3">
                                <button type="submit" class="btn btn-primary">Filtrer</button>
                            </div>
                        </form>

                        <div class="table-responsive mt-4">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Id</th>
                                        <th>Nom</th>
                                        <th>Prix</th>
                                        <th>Type</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        // Récupérer la liste des produits filtrés depuis l'attribut "produits"
                                        List<Produit> produits = (List<Produit>) request.getAttribute("produits");
                                        if (produits != null && !produits.isEmpty()) {
                                            for (Produit produitt : produits) {
                                    %>
                                    <tr>
                                        <td><%= produitt.getIdProduit() %></td>
                                        <td><%= produitt.getNomProduit() %></td>
                                        <td><%= produitt.getPrix() %> Ar </td>
                                        <td><%= produitt.getIdTypeProduit() %></td>
                                        <td>
                                            <a href="ProduitController?action=edit&id=<%= produitt.getIdProduit() %>" class="btn btn-warning btn-sm">Modifier</a>
                                            <a href="ProduitController?action=delete&id=<%= produitt.getIdProduit() %>" class="btn btn-danger btn-sm">Supprimer</a>
                                        </td>
                                    </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                    <tr>
                                        <td colspan="5" class="text-center">Aucun produit trouvé.</td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
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
