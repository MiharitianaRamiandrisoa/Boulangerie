<%-- 
    Document   : Insertion_produit
    Created on : 2 janv. 2025, 11:17:50
    Author     : MIHARY
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.TypeProduit" %>
<%@ page import="Model.Produit" %>
<%@ page import="Model.Ingredient" %>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="partials/_head.jsp" />

<% 
    List<Produit> produit = (List<Produit>) request.getAttribute("produit");
    List<Ingredient> ingredient = (List<Ingredient>) request.getAttribute("ingredient");
%>

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
                <h4 class="card-title">Creation de recette</h4>

                <form class="forms-sample" method="POST" action="RecetteController">
                    <div class="form-group">
                        <label for="idTypeProduit">Produit</label>
                        <select class="form-control" id="idTypeProduit" name="produit" required>
                            
                            <% for( Produit p : produit ){ %>
                            <option value="<%= p.getIdProduit() %>"> <%= new Produit().getNomProduit(p.getIdProduit(), null) %> </option>
                            <%}%>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="Ingredient">Ingredient</label>
                        <select class="form-control" id="idTypeProduit" name="ingredient" required>
                            <% for (Ingredient i : ingredient){ %>
                            <option value="<%= i.getIdIngredient() %>"><%=i.getNomIngredient()%></option>
                            <%}%>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="nomProduit">Quantite</label>
                        <input type="number" class="form-control" id="quantite" name="quantite"
                               placeholder="Quantite" required>
                    </div>

                    <button type="submit" class="btn btn-primary mr-2"> ok </button>
                    <button type="reset" class="btn btn-light">Annuler</button>
                </form>

                <%-- Affichage du message d'erreur, si nécessaire --%>
                <% if (request.getAttribute("errorMessage") != null) { %>
                    <div class="alert alert-danger mt-3">
                        <%= request.getAttribute("errorMessage") %>
                    </div>
                <% } %>

              </div>
            </div>
          </div>

          <jsp:include page="partials/_footer.jsp" />
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
