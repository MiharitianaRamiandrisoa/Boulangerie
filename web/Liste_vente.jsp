<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.Vente" %>
<%@ page import="Model.VenteDetail" %>
<%@ page import="Model.Produit" %>
<%@ page import="Model.TypeProduit" %>
<%@ page import="Model.Parfum" %>
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
                        <h4 class="card-title">Liste des Ventes</h4>

                            <!-- Formulaire de recherche -->
                            <form method="GET" action="VenteController">
                                <input type="hidden" name="action" value="liste">
                                <div class="form-row">
                                    <!-- Champ pour le type de produit -->
                                    <div class="col-md-3">
                                        <label for="typeProduit">Type de produit</label>
                                        <select id="typeProduit" name="typeProduit" class="form-control">
                                            <option value="">Sélectionner un type</option>
                                            <%
                                                // Récupérer la liste des types de produits
                                                List<TypeProduit> typesProduit = (List<TypeProduit>) request.getAttribute("typesProduit");
                                                if (typesProduit != null) {
                                                    for (TypeProduit type : typesProduit) {
                                            %>
                                            <option value="<%= type.getIdTypeProduit() %>"><%= type.getNomType() %></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
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
 
                                </div>

                                <div class="form-row mt-3">
                                    <button type="submit" class="btn btn-primary">Filtrer</button>
                                </div>
                            </form>



                        <!-- Liste des ventes -->
                        <div class="table-responsive mt-4">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>ID Vente</th>
                                        <th>Date</th>
                                        <th>Produits</th>
                                        <th>Categorie</th>
                                        <th>Prix</th>
                                        <th>Quantite</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        List<VenteDetail> ventes = (List<VenteDetail>) request.getAttribute("vente");
                                        if (ventes != null && !ventes.isEmpty()) {
                                             
                                            for (VenteDetail vd : ventes){ %>

                                                    <tr>
                                                    <td><%= vd.getIdVente() %></td>
                                                    <td><%= vd.getDate() %></td>
                                                    <td><%=Produit.getNomProduit(vd.getIdProduit(),null  )  %></td>
                                                    <td><%=TypeProduit.getNomById(vd.getIdType(),null  )  %></td>
                                                    <td><%=vd.getPrix() %></td>
                                                    <td><%= vd.getQtt() %></td>
                                                </tr>
                                                    
                                                <%}
                                        } else {
                                    %>
                                    <tr>
                                        <td colspan="4" class="text-center">Aucune vente trouvÃ©e.</td>
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
