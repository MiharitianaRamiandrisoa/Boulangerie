<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.TypeProduit" %>
<%@ page import="Model.Produit" %>
<<<<<<< Updated upstream
=======
<%@ page import="Model.Client" %>
<%@ page import="Model.Vendeur" %>
>>>>>>> Stashed changes

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
                                <h4 class="card-title">Insertion Vente</h4>

                                <!-- Message d'erreur -->
                                <% 
                                    String errorMessage = (String) request.getAttribute("errorMessage");
                                    if (errorMessage != null) { 
                                %>
                                <div class="alert alert-danger" role="alert">
                                    <%= errorMessage %>
                                </div>
                                <% } %>

                                <form class="forms-sample" method="POST" action="VenteController" >
                                    <div class="form-group">
                                        <label for="produit">Produit</label>
                                        <select class="form-control" name="produit" id="produit">
                                        <% 
                                            List<Produit> produits = (List<Produit>) request.getAttribute("produits");
                                            if (produits != null) {
                                                for (Produit prod : produits) { 
                                        %>
                                            <option value="<%= prod.getIdProduit() %>"><%= prod.getNomProduit() %></option>
                                        <%
                                                }
                                            } else { 
                                        %>
                                            <option disabled>Aucun produit disponible</option>
                                        <% } %>
                                        </select>
                                    </div>
                                        <div class="form-group">
                                        <label for="vendeur">Vendeur</label>
                                        <select class="form-control" name="vendeur">
                                        <% 
                                            List<Vendeur> vendeur = (List<Vendeur>) request.getAttribute("vendeur");
                                            if (vendeur != null) {
                                                for (Vendeur v : vendeur) { 
                                        %>
                                            <option value="<%= v.getIdVendeur() %>"><%=Vendeur.getNomVendeurById( null, v.getIdVendeur()) %></option>
                                        <%
                                                }
                                            } else { 
                                        %>
                                            <option disabled></option>
                                        <% } %>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                      <label for="qttIngredient">Quantite</label>
                                      <input type="number" class="form-control" name="qttProduit" placeholder="Quantite">
                                    </div>

                                    <div class="form-group">
                                        <label for="date">Date</label>
                                        <input type="date" class="form-control" name="date" placeholder="Date">
                                    </div>

                                    <button type="submit" class="btn btn-primary mr-2">Submit</button>
                                    <button class="btn btn-light">Cancel</button>
                                </form>
                            </div>
                        </div>
                    </div>
                    <jsp:include page="partials/_footer.jsp" />
                </div>
            </div>   
        </div>
    </div>

  <!-- Plugins JS -->
  <script src="vendors/js/vendor.bundle.base.js"></script>
  <!-- End inject -->
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
