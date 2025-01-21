<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.Produit" %>
<%@ page import="Model.TypeProduit" %>
<%@ page import="Model.Ingredient" %>

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
                            <h4 class="card-title">Liste des Produits</h4>

                            <!-- Formulaire de recherche -->
                            <form method="GET" action="ProduitController">
                                <input type="hidden" name= "action" value="listProduits" >
                                <div class="form-row">
                                     <!--Champ pour le type de produit--> 
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

                                     <!--Champ pour le type de produit--> 
                                    <div class="col-md-3">
                                        <label for="typeProduit">Ingredient</label>
                                        <select id="typeProduit" name="ingredient" class="form-control">
                                            <option value="">Sélectionner un ingredient</option>
                                            <%
                                                // Récupérer la liste des types de Ingredient
                                                List<Ingredient> ingredient = (List<Ingredient>) request.getAttribute("ingredient");
                                                if (ingredient != null) {
                                                    for (Ingredient i : ingredient) {
                                            %>
                                            <option value="<%= i.getIdIngredient() %>"><%=i.getNomIngredient() %></option>
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

                            <div class="table-responsive mt-4">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Id</th>
                                            <th>Nom</th>
                                            <th>Prix</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        // Récupérer la liste des produits filtrés depuis l'attribut "produits"
                                        List<Produit> produits = (List<Produit>) request.getAttribute("produits");
                                        if (produits != null && !produits.isEmpty()) {
                                            for (Produit produit : produits) {%>
                                                <tr>
                                                    <td><%= produit.getIdProduit() %></td>
                                                    <td><%= produit.getNomProduit() %></td>
                                                    <td><%= produit.getPrix() %> Ar </td>

                                                    <td>
                                                        <button
                                                            class="btn btn-warning btn-sm ingredientButton" 
                                                            data-bs-toggle="modal"
                                                            data-id="<%= produit.getIdProduit() %>"
                                                            data-bs-target="#ingredientModal">
                                                            Ingrédients
                                                        </button>
                                                     </td>
                                                </tr>
                                            <% }
                                        } else{%>
                                            <tr>
                                                <td colspan="5" class="text-center">Aucun produit trouvé.</td>
                                            </tr>
                                        <%}%>
                                    </tbody>
                                </table>
                                <!-- Modale pour afficher les ingrédients -->
                                <div class="modal fade" id="ingredientModal" tabindex="-1" aria-labelledby="ingredientModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="ingredientModalLabel">Ingrédients du Produit</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <!-- Liste des ingrédients (affichée par le serveur) -->
                                                <ul id="ingredientList">
                                                    <%
                                                        // Récupérer la liste des ingrédients à partir de l'attribut 'ingredients' (si disponible)
                                                        List<Ingredient> ingredients = (List<Ingredient>) request.getAttribute("ingredients");
                                                        if (ingredients != null) {
                                                        out.println("eto zan zao");
                                                            for (Ingredient in : ingredients) {
                                                                %>
                                                                <li><%= in.getNomIngredient() %></li>
                                                                <%
                                                            }
                                                        }
                                                    %>
                                                </ul>
                                            </div>
                                            <div class="modal-footer">
                                              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fermer</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

          <jsp:include page="partials/_footer.jsp" />
        </div>
      </div>   
    </div>
      
      
      <script>
    function ajouterAuConseilMois(produitId) {
        // Récupérer les valeurs des champs 'mois' et 'annee'
        const mois = document.getElementById('mois').value;
        const annee = document.getElementById('annee').value;

        // Créer un formulaire dynamique pour envoyer les données
        var form = document.createElement("form");
        form.method = "POST";
        form.action = "ProduitController"; // L'action du contrôleur

        // Ajouter les champs cachés
        var actionInput = document.createElement("input");
        actionInput.type = "hidden";
        actionInput.name = "action";
        actionInput.value = "addConseilMois"; // L'action pour ajouter au conseil du mois
        form.appendChild(actionInput);

        var produitIdInput = document.createElement("input");
        produitIdInput.type = "hidden";
        produitIdInput.name = "produitId";
        produitIdInput.value = produitId; // L'ID du produit
        form.appendChild(produitIdInput);

        var moisInput = document.createElement("input");
        moisInput.type = "hidden";
        moisInput.name = "mois";
        moisInput.value = mois; // Le mois sélectionné
        form.appendChild(moisInput);

        var anneeInput = document.createElement("input");
        anneeInput.type = "hidden";
        anneeInput.name = "annee";
        anneeInput.value = annee; // L'année sélectionnée
        form.appendChild(anneeInput);

        // Ajouter le formulaire au body et soumettre
        document.body.appendChild(form);
        form.submit();
    }
</script>

      
      
      <script>
        document.addEventListener('click', function (event) {
            // Vérifier si l'élément cliqué est un bouton avec la classe 'ingredientButton'
            if (event.target && event.target.classList.contains('ingredientButton')) {
                var produitId = event.target.getAttribute('data-id'); // Récupérer l'ID du produit

                // Effectuer une requête AJAX avec Fetch pour récupérer les ingrédients
                fetch('ProduitController?action=getIngrédients&produitId=' + produitId)
                    .then(response => response.text())  // Lire la réponse sous forme de texte
                    .then(data => {
                        document.getElementById('ingredientList').innerHTML = data;             
                    })
                    .catch(error => {
                        console.error('Erreur:', error);
                    });
            }
        });
      </script>
      
        <script>
            function voirProduitConseil() {
                // Récupérer les valeurs des champs
                const mois = document.getElementById('mois').value;
                const annee = document.getElementById('annee').value;

                // Rediriger vers le contrôleur avec les paramètres
                const url = "ProduitController?action=viewConseilMois&mois=" + mois +"&annee="+annee;
                window.location.href = url;
            }
        </script>
  
  <!-- plugins:js -->
  <script src="vendors/js/vendor.bundle.base.js"></script>
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
