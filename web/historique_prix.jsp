<!DOCTYPE html>
<html lang="en">

<jsp:include page="partials/_head.jsp" />

<%@ page import="java.util.List" %>
<%@ page import="Model.PrixProduit "%>
<%@ page import="Model.Produit "%>

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
                          <h4 class="card-title">Historique</h4>
                          
                          <form method="GET" action="PrixProduitController">
                                <input type="hidden" name= "action" value="listePrix">
                                <div class="form-row">
                                    <div class="col-md-3">
                                        <label for="produit">produit</label>
                                        <select id="typeProduit" name="produit" class="form-control">
                                            <option value="">Sélectionner un produit</option>
                                            <%
                                                // Récupérer la liste des types de produits
                                                List<Produit> produits = (List<Produit>) request.getAttribute("produit");
                                                if (produits != null) {
                                                    for (Produit p : produits) { %>
                                                        <option value="<%= p.getIdProduit() %>"><%= p.getNomProduit() %></option> <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-row mt-3">
                                    <button type="submit" class="btn btn-primary">Filtrer</button>
                                </div>
                            </form>

                          <div class="table-responsive">
                              <table class="table">
                                  <thead>
                                      <tr>
                                          <th>Produit</th>
                                          <th>Prix</th>
                                          <th>Date</th>
                                      </tr>
                                  </thead>
                                  <tbody>
                                      <% 
                                          List<PrixProduit> prixProduit = (List<PrixProduit>) request.getAttribute("prixProduit");
                                          if (prixProduit != null && !prixProduit.isEmpty()) {
                                              for (PrixProduit pp : prixProduit) {
                                      %>
                                      <tr>
                                          <td><%=  Produit.getNomProduit( pp.getIdProduit(),null )%></td>
                                          <td><%= pp.getPrix() %></td>
                                          <td><%= pp.getDate() %></td>
                                      </tr>
                                      <%    }
                                          } else {
                                      %>
                                      <tr>
                                          <td colspan="4" class="text-center">Aucun prix trouve.</td>
                                      </tr>
                                      <% } %>
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
