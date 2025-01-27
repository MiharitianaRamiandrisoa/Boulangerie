<%-- 
    Document   : Liste_stock_ingredient
    Created on : 10 janv. 2025, 10:12:17
    Author     : MIHARY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.Vente" %>
<%@ page import="Model.Client" %>

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
                                <h4 class="card-title">Liste des Clients</h4>
                                
                                <form method="GET" action="VenteClientController">
                                    <input type="hidden" name="action" value="venteClient">
                                    <div class="form-row">
                                        <!-- Champ pour la date -->
                                        <div class="col-md-3">
                                            <label for="date">Date</label>
                                            <input type="date" id="nomProduit" name="date" class="form-control" placeholder="Date">
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-info">
                                        valider
                                    </button>
                                </form>
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th> Id </th>
                                                <th>Nom</th>
                                                <th> Date </th>
                                                <th> Id Vente </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                List<Vente> ventes = (List<Vente>) request.getAttribute("vente");
                                                if (ventes != null && !ventes.isEmpty()) {
                                                    for (Vente v : ventes) {
                                            %>
                                            <tr>
                                                <td><%= v.getClient()%></td>
                                                <td><%= new Client().getNomClientById( null, v.getClient()) %></td>
                                                <td><%= v.getDate() %></td>
                                                <td><%= v.getIdVente() %></td>
                                            </tr>
                                            <%
                                                    }
                                                } else {
                                            %>
                                            <tr>
                                                <td colspan="2" class="text-center">Aucune Client aujourd'hui.</td>
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
