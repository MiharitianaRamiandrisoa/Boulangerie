<%-- 
    Document   : Achat_ingredient
    Created on : 10 janv. 2025, 13:42:02
    Author     : MIHARY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="Model.Ingredient"%>

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
                        <h4 class="card-title">Achat ingrédient</h4>
                        <form class="forms-sample" action="AchatIngredientController" method="post">
                            <div class="form-group">
                                <label for="ingredient">Ingrédient</label>
                                <select class="form-control" name="ingredient" id="ingredient" required>
                                <% 
                                    List<Ingredient> ingredients = (List<Ingredient>) request.getAttribute("ingredient");
                                    if (ingredients != null) {
                                        for (Ingredient ingredient : ingredients) { 
                                %>
                                    <option value="<%= ingredient.getIdIngredient() %>">
                                        <%= ingredient.getNomIngredient() %> 
                                    </option>
                                <% 
                                        }
                                    } else { 
                                %>
                                    <option disabled>Aucun ingrédient disponible</option>
                                <% } %>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="quantite">Quantité</label>
                                <input type="number" class="form-control" name="quantite" id="quantite" placeholder="Quantité" required>
                            </div>

                            <div class="form-group">
                                <label for="date">Date</label>
                                <input type="date" class="form-control" name="date" id="date">
                            </div>

                            <button type="submit" class="btn btn-primary mr-2">Enregistrer</button>
                            <button type="reset" class="btn btn-light">Annuler</button>
                        </form>
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
