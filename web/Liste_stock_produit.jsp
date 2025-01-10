<%-- 
    Document   : Liste_stock
    Created on : 3 janv. 2025, 14:29:18
    Author     : MIHARY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.StockProduit" %>
<%@ page import="Model.Produit" %>

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
                                <h4 class="card-title">Liste des Produits en Stock</h4>

                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>ID Produit</th>
                                                <th>Nom Produit</th>
                                                <th>Stock Disponible</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                List<StockProduit> stocks = (List<StockProduit>) request.getAttribute("stocks");
                                                if (stocks != null && !stocks.isEmpty()) {
                                                    for (StockProduit stock : stocks) {
                                            %>
                                            <tr>
                                                <td><%= stock.getIdProduit() %></td>
                                                <td><%= new Produit().getNomProduit(stock.getIdProduit(), null) %></td>
                                                <td><%= stock.getStockDisponible() %></td>
                                            </tr>
                                            <%
                                                    }
                                                } else {
                                            %>
                                            <tr>
                                                <td colspan="3" class="text-center">Aucun produit en stock.</td>
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
                                        String baseUrl = "StockController?page=";

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
