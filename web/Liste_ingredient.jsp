<!DOCTYPE html>
<html lang="en">

<jsp:include page="partials/_head.jsp" />

<%@ page import="java.util.List" %>
<%@ page import="Model.Ingredient" %>
<%@ page import="Model.Unite" %>

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
                        <h4 class="card-title">Liste Ingredient</h4>

                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Id</th>
                                        <th>Nom</th>
                                        <th>Unite</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% 
                                        List<Ingredient> ingredients = (List<Ingredient>) request.getAttribute("ingredients");
                                        if (ingredients != null && !ingredients.isEmpty()) {
                                            for (Ingredient ingredient : ingredients) {
                                    %>
                                    <tr>
                                        <td><%= ingredient.getIdIngredient() %></td>
                                        <td><%= ingredient.getNomIngredient() %></td>
                                        <td><%= Unite.getNomById(ingredient.getUnite(),null)  %></td>
                                        <td>
                                            <a href="IngredientController?action=edit&id=<%= ingredient.getIdIngredient() %>" class="btn btn-primary btn-sm">Modifier</a>
                                            <a href="IngredientController?action=delete&id=<%= ingredient.getIdIngredient() %>" class="btn btn-danger btn-sm">Supprimer</a>
                                        </td>
                                    </tr>
                                    <% 
                                            }
                                        } else {
                                    %>
                                    <tr>
                                        <td colspan="4" class="text-center">Aucun ingrï¿½dient trouvï¿½.</td>
                                    </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>

                        <!-- Pagination -->
                        <div class="pagination mt-3">
                            <% 
                                int currentPage = (int) request.getAttribute("currentPage");
                                int totalPages = (int) request.getAttribute("totalPages");
                                int itemsPerPage = (int) request.getAttribute("itemsPerPage");
                                
                                String baseUrl = "IngredientController?action=Liste&itemsPerPage=" + itemsPerPage + "&page=";

                                // Afficher un lien pour la page prï¿½cï¿½dente si ce n'est pas la premiï¿½re page
                                if (currentPage > 1) {
                            %>
                                <a href="<%= baseUrl + (currentPage - 1) %>" class="btn btn-outline-secondary">Précédent</a>
                            <% 
                                }

                                // Afficher les liens pour chaque page
                                for (int i = 1; i <= totalPages; i++) {
                            %>
                                <a href="<%= baseUrl + i %>" class="btn <%= (i == currentPage) ? "btn-primary" : "btn-outline-secondary" %>">
                                    <%= i %>
                                </a>
                            <% 
                                }

                                // Afficher un lien pour la page suivante si ce n'est pas la derniï¿½re page
                                if (currentPage < totalPages) {
                            %>
                                <a href="<%= baseUrl + (currentPage + 1) %>" class="btn btn-outline-secondary">Suivant</a>
                            <% 
                                } 
                            %>
                        </div>
                        <!-- Fin Pagination -->

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
  <!-- endinject -->
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
