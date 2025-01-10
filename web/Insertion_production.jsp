<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="Model.Produit"%>

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
                        
                        <h4 class="card-title">Insertion Production</h4>
                        
                            <%-- Affichage de l'erreur si elle existe --%>
                            <% 
                                String errorMessage = (String) request.getAttribute("errorMessage");
                                if (errorMessage != null && !errorMessage.isEmpty()) {
                            %>
                                <div style="color: red;">
                                    <strong>Erreur : </strong><%= errorMessage %>
                                </div>
                            <% 
                                }
                            %>
                        <form class="forms-sample" action="ProductionController" method="post">
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
                                <label for="quantite">Quantité</label>
                                <input type="number" class="form-control" name="quantite" id="quantite" placeholder="Quantité" required>
                            </div>

                            <div class="form-group">
                              <label for="date">Date</label>
                              <input type="date" class="form-control" name="date" id="date" placeholder="Date" >
                          </div>

                            <button type="submit" class="btn btn-primary mr-2">Submit</button>
                            <button type="reset" class="btn btn-light">Cancel</button>
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
