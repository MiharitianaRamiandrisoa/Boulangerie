<%-- 
    Document   : Insertion_type.jsp
    Created on : 9 janv. 2025, 12:31:35
    Author     : MIHARY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Model.TypeProduit" %>

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
                <h4 class="card-title">Insertion Type de Produit</h4>

                <!-- Formulaire d'insertion de type produit -->
                <form class="forms-sample" method="POST" action="TypeController">
                    <% 
                      TypeProduit typeProduit = (TypeProduit) request.getAttribute("typeProduit"); 
                    %>
                    <input type="hidden" name="action" value="<%= (typeProduit != null) ? "update" : "insert" %>">
                    <% if (typeProduit != null) { %>
                        <input type="hidden" name="idTypeProduit" value="<%= typeProduit.getIdTypeProduit() %>">
                    <% } %>

                    <div class="form-group">
                        <label for="nomType">Nom du Type</label>
                        <input type="text" class="form-control" id="nomType" name="nomType"
                               value="<%= (typeProduit != null) ? typeProduit.getNomType() : "" %>"
                               placeholder="Nom du type de produit" required>
                    </div>

                    <button type="submit" class="btn btn-primary mr-2">
                        <%= (typeProduit != null) ? "Modifier" : "Ajouter" %>
                    </button>
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
