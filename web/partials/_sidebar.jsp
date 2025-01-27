<nav class="sidebar sidebar-offcanvas" id="sidebar">
    <ul class="nav">
        <li class="nav-item">
            <a class="nav-link" href="index.jsp">
                <i class="fas fa-home menu-icon"></i>
                <span class="menu-title">Accueil</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#produit" aria-expanded="false" aria-controls="ui-basic">
                <i class="fas fa-bread-slice menu-icon"></i>
                <span class="menu-title">Catalogue</span>
                <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="produit">
                <ul class="nav flex-column sub-menu">
                    <li class="nav-item"> <a class="nav-link" href="ProduitController?action=listProduits">Liste des produits</a></li>
                    <li class="nav-item"> <a class="nav-link" href="IngredientController?action=Liste">Liste des ingrédients</a></li>
                </ul>
            </div>
        </li>

        <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#ingredient" aria-expanded="false" aria-controls="ui-basic">
                <i class="fas fa-cogs menu-icon"></i>
                <span class="menu-title">Gestion</span>
                <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="ingredient">
                <ul class="nav flex-column sub-menu">
                    <li class="nav-item"> <a class="nav-link" href="ProduitController">Ajouter un produit</a></li>
                    <li class="nav-item"> <a class="nav-link" href="IngredientController">Ajouter un ingrédient</a></li>
                    <li class="nav-item"> <a class="nav-link" href="RecetteController">Création de recette</a></li>
                    <li class="nav-item"> <a class="nav-link" href="TypeController">Insertion type</a></li>
                </ul>
            </div>
        </li>

        <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#achat" aria-expanded="false" aria-controls="ui-basic">
                <i class="fas fa-shopping-cart menu-icon"></i>
                <span class="menu-title">Achat</span>
                <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="achat">
                <ul class="nav flex-column sub-menu">
                    <li class="nav-item"> <a class="nav-link" href="AchatIngredientController">Achat ingrédient</a></li>
                </ul>
            </div>
        </li>

        <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#production" aria-expanded="false" aria-controls="ui-basic">
                <i class="fas fa-hammer menu-icon"></i>
                <span class="menu-title">Fabrication</span>
                <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="production">
                <ul class="nav flex-column sub-menu">
                    <li class="nav-item"> <a class="nav-link" href="ProductionController">Nouvelle production</a></li>
                    <li class="nav-item"> <a class="nav-link" href="ProductionController?action=liste">Liste des productions</a></li>
                </ul>
            </div>
        </li>

        <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#stock" aria-expanded="false" aria-controls="ui-basic">
                <i class="fas fa-boxes menu-icon"></i>
                <span class="menu-title">Stock</span>
                <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="stock">
                <ul class="nav flex-column sub-menu">
                    <li class="nav-item"> <a class="nav-link" href="StockController">Produit</a></li>
                    <li class="nav-item"> <a class="nav-link" href="StockController?type=ingredient">Ingrédient</a></li>
                </ul>
            </div>
        </li>

        <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#vente" aria-expanded="false" aria-controls="ui-basic">
                <i class="fas fa-cash-register menu-icon"></i>
                <span class="menu-title">Vente</span>
                <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="vente">
                <ul class="nav flex-column sub-menu">
                    <li class="nav-item"> <a class="nav-link" href="VenteController">Insertion vente</a></li>
                    <li class="nav-item"> <a class="nav-link" href="VenteController?action=liste">Liste des ventes</a></li>
                </ul>
            </div>
        </li>
        
        <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#client" aria-expanded="false" aria-controls="ui-basic">
                <i class="fas fa-cash-register menu-icon"></i>
                <span class="menu-title">client</span>
                <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="client">
                <ul class="nav flex-column sub-menu">
                    <li class="nav-item"> <a class="nav-link" href="VenteClientController?action=venteClient">Liste client-vente</a></li>
                </ul>
            </div>
        </li>
        
        <li class="nav-item">
            <a class="nav-link"  href="ConseilController" method="GET" >
                <i class="fas fa-home menu-icon"></i>
                <span class="menu-title">Ajout Produit Conseil</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link"  href="VendeurController" method="GET" >
                <i class="fas fa-home menu-icon"></i>
                <span class="menu-title">Commission vendeur</span>
            </a>
        </li>
    </ul>
</nav>
