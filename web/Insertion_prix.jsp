<%-- 
    Document   : InsertionIngredient
    Created on : 2 janv. 2025, 12:57:10
    Author     : MIHARY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import= "Model.Ingredient" %>
<%@page import= "Model.Unite" %>
<%@page import= "Model.Produit" %>
<%@page import= "java.util.List" %>

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
          <!-- Main -->
          <div class="offset-1 col-md-10 grid-margin stretch-card">
            <div class="card">
              <div class="card-body">
                <h4 class="card-title">Insertion Prix</h4>

                <!-- Formulaire d'insertion d'ingrédient -->
                <form class="forms-sample" method="POST" action="PrixProduitController">
                  <!-- Si un ID est présent, c'est une modification -->

                  <div class="form-group">
                      <label for="uniteIngredient">Produit</label>
                      <select class="form-control" id="unite" name="produit" required>
                        <option value="">Sélectionner un produit</option>
                        <% 
                          List<Produit> produits = (List<Produit>) request.getAttribute("produit");
                          
                          for (Produit produit : produits) {
                        %>
                          <option value="<%= produit.getIdProduit()%>" ><%= produit.getNomProduit() %>
                          </option>
                        <% } %>
                      </select>
                    </div>
                  
                  <div class="form-group">
                    <label for="prix"></label>
                    <input type="number" class="form-control" name="prix"  placeholder="Prix">
                  </div>
                
                <div class="form-group">
                    <label for="date"></label>
                    <input type="date" class="form-control" name="date"  placeholder="Date">
                </div>
                      <input type="submit" >
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
