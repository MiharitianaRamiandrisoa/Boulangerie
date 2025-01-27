<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="Model.Vente" %>
<%@ page import="Model.VenteDetail" %>
<%@ page import="Model.Produit" %>
<%@ page import="Model.TypeProduit" %>
<%@ page import="Model.Parfum" %>
<%@ page import="Model.Client" %>
<%@ page import="Model.Vendeur" %>
<%@ page import="Model.Genre" %>

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
                        <h4 class="card-title">Liste des Vendeurs</h4>

                            <!-- Formulaire de recherche -->
                            <form method="GET" action="VendeurController">
                                <div class="form-row">
                                    <!-- Champ pour la date du conseil -->
                                    <div class="col-md-3">
                                        <label for="mois">Mois</label>
                                        <select id="mois" name="mois" class="form-control">
                                            <option value="">Sélectionner un mois</option>
                                            <%
                                                String[] mois = { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre" };
                                                for (int i = 1; i <= 12; i++) {
                                            %>
                                            <option value="<%= i %>"><%= mois[i - 1] %></option>
                                            <% } %>
                                        </select>
                                    </div>

                                        
                                    

                                    <!-- Champ pour l'année -->
                                    <div class="col-md-3">
                                        <label for="annee">Année</label>
                                        <input type="number" id="annee" name="annee" class="form-control" placeholder="Année">
                                    </div>
                                </div>

                                        
                                
                                        
                                <div class="form-row mt-3">
                                    <button type="submit" class="btn btn-primary">valider</button>
                                </div>
                            </form>

                        <!-- Liste des ventes -->
                        <div class="table-responsive mt-4">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Genre</th>
                                        <th>Commission Totale</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        // Récupérer la Map de commissions par genre
                                        Map<String, Double> commissions = (Map<String, Double>) request.getAttribute("commissions");

                                        if (commissions != null && !commissions.isEmpty()) {
                                            // Parcours de la Map et affichage des résultats
                                            for (Map.Entry<String, Double> entry : commissions.entrySet()) {
                                    %>
                                    <tr>
                                        <td><%= entry.getKey() %></td>
                                        <td><%= entry.getValue() %></td>
                                    </tr>
                                    <% 
                                            }
                                        } else {
                                    %>
                                    <tr>
                                        <td colspan="2" class="text-center">Aucune vente trouvée.</td>
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
  
  <script src="vendors/js/vendor.bundle.base.js"></script>
  <script src="js/off-canvas.js"></script>
  <script src="js/hoverable-collapse.js"></script>
  <script src="js/template.js"></script>
</body>
</html>
