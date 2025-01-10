<%-- 
    Document   : Liste_production
    Created on : 3 janv. 2025, 15:30:48
    Author     : MIHARY
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="Model.Production"%>
<%@ page import="Model.Produit" %>
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
                        <h4 class="card-title">Liste des Productions</h4>

                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>ID de Production</th>
                                        <th>Quantité</th>
                                        <th>Date</th>
                                        <th>Produit</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        // Récupérer la liste des productions depuis l'attribut "productions"
                                        List<Production> productions = (List<Production>) request.getAttribute("productions");
                                        if (productions != null && !productions.isEmpty()) {
                                            for (Production production : productions) {
                                    %>
                                    <tr>
                                        <td><%= production.getIdProduction() %></td>
                                        <td><%= production.getQtt() %></td>
                                        <td><%= production.getDate() %></td>
                                        <td><%= Produit.getNomProduit(production.getIdProduit() , null)  %></td> <!-- Assurez-vous que la méthode getProduit() existe si nécessaire -->
                                        
                                    </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                    <tr>
                                        <td colspan="5" class="text-center">Aucune production trouvée.</td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                                <!-- Pagination -->
                            <div class="pagination mt-3">
                                <%
                                    int currentPage = (int) request.getAttribute("currentPage");
                                    int totalPages = (int) request.getAttribute("totalPages");
                                    String baseUrl = "ProductionController?action=liste&page=";

                                    if (currentPage > 1) {
                                %>
                                <a href="<%= baseUrl + (currentPage - 1) %>" class="btn btn-outline-secondary">Précédent</a>
                                <% } %>

                                <% for (int i = 1; i <= totalPages; i++) { %>
                                <a href="<%= baseUrl + i %>" class="btn <%= (i == currentPage) ? "btn-primary" : "btn-outline-secondary" %>">
                                    <%= i %>
                                </a>
                                <% } %>

                                <% if (currentPage < totalPages) { %>
                                <a href="<%= baseUrl + (currentPage + 1) %>" class="btn btn-outline-secondary">Suivant</a>
                                <% } %>
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