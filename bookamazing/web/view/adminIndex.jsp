<c:if test="${sessionScope.admin != true}">
    <script>
        window.location.assign("/bookamazing/view/clienteIndex");
    </script>
</c:if>
<div class="container-fluid">
    <%-- Buscas --%>
    <div class="panel-group col-lg-2 col-xs-12">
        <ul class="nav nav-list">
            <li class="nav-tabs">Tipo:</li>
            <li><a href="adminIndex?tipo=literatura">Literatura</a></li>
            <li><a href="adminIndex?tipo=didatico">Didático</a></li>
            <li class="nav-divider"></li>
            <li class="nav-tabs">Área de Conhecimento:</li>
            <li><a href="adminIndex?tipo=didatico&area=informatica">Informática</a></li>
            <li><a href="adminIndex?tipo=didatico&area=ciencia">Ciência</a></li>
            <li><a href="adminIndex?tipo=literatura&area=romance">Romance</a></li>
            <li><a href="adminIndex?tipo=literatura&area=suspense">Suspense</a></li>
            <li><a href="adminIndex?tipo=literatura&area=ficcao">Ficção</a></li>
        </ul>
    </div>
    <%-- Livros --%>
    <div class="col-lg-9 col-xs-12">
        <c:forEach var="livro" items="${sessionScope.livraria.getLivros()}">
            <div class="panel col-lg-3 col-xs-10 col-xs-offset-1 text-center">
                <a href='javascript:showModalDescribe(${sessionScope.livraria.getLivroJSON(livro.getIdString())})' class="thumbnail"><img src="../imagensLivro/${livro.getCaminho_imagem()}" alt="image"></a>
                <h5 class="text-center">${livro.getTitulo()}</h5>
                <c:url var="url" value="clienteIndex">
                    <c:param name="addCart" value="${livro.getId()}"></c:param>
                </c:url>
                <div class="row">
                    <a href='javascript:showModalDescribe(${sessionScope.livraria.getLivroJSON(livro.getIdString())})' class="btn btn-default"><span class="glyphicon glyphicon-eye-open"></span> Descrição</a>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <script type="text/javascript">
        function showModalDescribe(strJson){
            
            $('#modalLivroTitulo').text(strJson.titulo);
            $('#modalLivroDescricao').text(strJson.descricao);
            $('#modalLivroAutor').text(strJson.nome_autor);
            $('#modalLivroTipo').text(strJson.tipo);
            $('#modalLivroArea').text(strJson.area_conhecimento);
            $('#modalLivroValor').text(strJson.valor + " R$");
            $('#modalLivroQuantidade').text(strJson.quantidade);
            $('#modalLivroCaminhoImagem').attr("src","../imagensLivro/"+strJson.caminho_imagem);
            
            $('#modalLivroAdicionar').attr("href","clienteIndex?addCart="+strJson.idLivro);
            
            $("#modalDescribeLivro").modal('show');
        }
    </script>
    
    <%-- Describre --%>
    <div id="modalDescribeLivro" class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content col-lg-12 col-xs-12">
                <div class="modal-header col-lg-12 col-xs-12">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                    <h4 id="modalLivroTitulo" class="modal-title"></h4>
                </div>
                <div class="modal-body panel-group col-lg-12 col-xs-12">
                    <div class="col-lg-4 col-xs-4">
                        <div class="panel panel-default">
                            <img class="img-thumbnail" id="modalLivroCaminhoImagem">
                        </div>
                        <div class="panel panel-default col-lg-12 col-xs-12">
                            <h6 id="modalLivroAutor" class="text-capitalize text-center"></h6>
                        </div>
                    </div>
                    <div class="col-lg-8 col-xs-8">
                        <div class="panel panel-default col-lg-12 col-xs-12">
                            <div class="panel panel-heading text-center col-lg-12 col-xs-12"><h5>Descricão</h5></div>
                            <div class="panel-body col-lg-12 col-xs-12">
                                <p id="modalLivroDescricao" class="caption small text-justify"></p>
                            </div>
                        </div>
                        <div class="panel panel-default col-lg-12 col-xs-12">
                            <table class="table text-center">
                                <tr>
                                    <td><h6>Tipo:</h6></td>
                                    <td><h6 id="modalLivroTipo" class="text-capitalize"></h6></td>
                                </tr>
                                <tr>
                                    <td><h6>Área:</h6></td>
                                    <td><h6 id="modalLivroArea" class="text-capitalize"></h6></td>
                                </tr>
                                <tr>
                                    <td><h6>Valor:</h6></td>
                                    <td><h6 id="modalLivroValor"></h6></td>
                                </tr>
                                <tr>
                                    <td><h6>Quantidade:</h6></td>
                                    <td><h6 id="modalLivroQuantidade"></h6></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div><%-- /.modal-content --%>
        </div><%-- /.modal-dialog --%>
    </div><%-- /.modal --%>
</div>
