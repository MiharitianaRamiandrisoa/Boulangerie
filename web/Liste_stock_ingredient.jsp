<%-- 
    Document   : Liste_stock_ingredient
    Created on : 10 janv. 2025, 10:12:17
    Author     : MIHARY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.StockIngredient" %>

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
                                <h4 class="card-title">Liste des Ingrédients en Stock</h4>

                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>ID Ingrédient</th>
                                                <th>Nom</th>
                                                <th>Quantité Disponible</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                List<StockIngredient> stocks = (List<StockIngredient>) request.getAttribute("stocks");
                                                if (stocks != null && !stocks.isEmpty()) {
                                                    for (StockIngredient stock : stocks) {
                                            %>
                                            <tr>
                                                <td><%= stock.getIdIngredient() %></td>
                                                <td><%= stock.getNomIngredient() %></td>
                                                <td><%= stock.getQttEnStock() %></td>
                                            </tr>
                                            <%
                                                    }
                                                } else {
                                            %>
                                            <tr>
                                                <td colspan="2" class="text-center">Aucun ingrédient en stock.</td>
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
                                        int currentPage = (request.getAttribute("currentPage") != null) ? (int) request.getAttribute("currentPage") : 1;
                                        int totalPages = (request.getAttribute("totalPages") != null) ? (int) request.getAttribute("totalPages") : 1;
                                        String baseUrl = "StockController?type=ingredient&page=";

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
