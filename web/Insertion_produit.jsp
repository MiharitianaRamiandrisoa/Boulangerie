<%-- 
    Document   : Insertion_produit
    Created on : 2 janv. 2025, 11:17:50
    Author     : MIHARY
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.TypeProduit" %>
<%@ page import="Model.Produit" %>
<%@ page import="Model.Parfum" %>

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
                <h4 class="card-title">Insertion Produit</h4>

                <!-- Formulaire d'insertion de produit -->
                <form class="forms-sample" method="POST" action="ProduitController">
                    <% Produit produit = (Produit) request.getAttribute("produit"); %>
                    <input type="hidden" name="action" value="<%= (produit != null) ? "update" : "insert" %>">
                    <% if (produit != null) { %>
                        <input type="hidden" name="id" value="<%= produit.getIdProduit() %>">
                    <% } %>

                    <div class="form-group">
                        <label for="nomProduit">Nom</label>
                        <input type="text" class="form-control" id="nomProduit" name="nomProduit"
                               value="<%= (produit != null) ? produit.getNomProduit() : "" %>"
                               placeholder="Nom du produit" required>
                    </div>

                    <div class="form-group">
                        <label for="idTypeProduit">Type</label>
                        <select class="form-control" id="idTypeProduit" name="idTypeProduit" required>
                            <option value="">Sélectionner une categorie </option>
                            <% 
                            List<TypeProduit> typesProduit = (List<TypeProduit>) request.getAttribute("typesProduit");
                            if (typesProduit != null) {
                                for (TypeProduit type : typesProduit) {
                                    boolean isSelected = (produit != null && produit.getIdTypeProduit() == type.getIdTypeProduit());
                            %>
                                <option value="<%= type.getIdTypeProduit() %>" <%= isSelected ? "selected" : "" %>>
                                    <%= type.getNomType() %>
                                </option>
                            <%
                                }
                            }
                            %>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="typeProduit">Parfum</label>
                        <select id="typeProduit" name="parfum" class="form-control">
                            <option value="">Sélectionner un Parfum</option>
                            <%
                                // Récupérer la liste des types de produits
                                List<Parfum> parfum = (List<Parfum>) request.getAttribute("parfum");
                                if (parfum != null) {
                                    for (Parfum parf : parfum ) {
                            %>
                            <option value="<%= parf.getIdParfum() %>"><%= parf  .getNom() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                        
                        
                    <div class="form-group">
                        <label for="nomProduit">Prix</label>
                        <input type="number" class="form-control" id="prix" name="prix"
                               value="<%= (produit != null) ? produit.getPrix() : "" %>"
                               placeholder="Nom du produit" required>
                    </div>

                    <button type="submit" class="btn btn-primary mr-2">
                        <%= (produit != null) ? "Modifier" : "Ajouter" %>
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
