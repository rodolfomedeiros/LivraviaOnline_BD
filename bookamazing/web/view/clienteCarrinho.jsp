<c:if test="${sessionScope.cliente == null && sessionScope.endereco == null}">
    <script>
        window.location.assign("/bookamazing/view/clienteIndex");
    </script>
</c:if>
<div class="container-fluid">
    <c:if test="${sessionScope.livrosDisponivel != null}">
        <div class="alert alert-warning alert-dismissible col-lg-6 col-lg-offset-3 col-xs-12" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span>&times;</span></button>
            <c:forEach var="livro" items="${sessionScope.livrosDisponivel}">
                <p class="small">O Livro <span class="text-negrito">${livro.getTitulo()}</span> só possui em estoque <span class="text-negrito">${livro.getQuantidade()}</span> unidades.</p>
            </c:forEach>
        </div> 
    </c:if>
    
    <div class="panel panel-default col-lg-6 col-xs-12 col-lg-offset-3"> 
        <div class="panel-heading text-center col-lg-12 col-xs-12 text-negrito">SEU CARRINHO DE COMPRAS!</div>
        <div class="panel-body col-lg-12 col-xs-12">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th></th>
                        <th>Título</th>
                        <th>Quantidade</th>
                        <th>SubTotal</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="livro" items="${sessionScope.cart.getLivros()}">
                        <tr>
                            <td class="col-lg-3 col-xs-4 text-center"><img src="../imagensLivro/${livro.getCaminho_imagem()}" style="width: 100%" alt="image"></td>
                            <td class="col-lg-4 col-xs-4 text-negrito">${livro.getTitulo()}</td>
                            <td class="col-lg-3 col-xs-4">
                                <a href="clienteCarrinho?atr=-1&id=${livro.getId()}" ><span class="glyphicon glyphicon-minus"></span></a>
                                <input type="text" class="text-center" style="width: 30px" value="${livro.getQuantidade()}" disabled>
                                <a href="clienteCarrinho?atr=1&id=${livro.getId()}" ><span class="glyphicon glyphicon-plus"></span></a>
                            </td>
                            <td class="col-lg-2">${livro.getValorTotalString()} R$</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="panel panel-default col-lg-3 col-xs-12">
        <div class="panel-heading col-lg-12 col-xs-12">
            <h4><span class="text-negrito">VALOR TOTAL: </span> ${sessionScope.cart.getTotalValorString()} R$</h4>
        </div>
        <div class="panel-body col-lg-12 col-xs-12"></div>
        <div class="panel-footer  col-lg-12 col-xs-12">
            <div class="col-lg-6 col-xs-0"></div>
            <div class="col-lg-6 col-xs-12">
                <a href="clienteFinalizarCompra" class="btn btn-yellow btn-lg"><span class="glyphicon glyphicon-share-alt"></span> Continuar</a>
            </div>
        </div>
    </div>
</div>
