<c:if test="${sessionScope.admin != true}">
    <script>
        window.location.assign("/bookamazing/view/clienteIndex");
    </script>
</c:if>
<div class="container-fluid">
    <div class="col-lg-3 col-xs-0"></div>
    <form method="post" enctype="multipart/form-data">       
        <div class="panel panel-default col-lg-6 col-xs-12">
            <div class="panel-heading text-center col-lg-12 col-xs-12"><h6>ADICIONE O LIVRO!</h6></div>
            <div class="panel-body col-lg-12 col-xs-12">
                <div class="col-lg-12 col-xs-12">
                    <label for="titulo">Título:</label>
                    <input type="text" name="titulo" class="form-control" required>
                </div>
                <div class="col-lg-6 col-xs-12">
                    <label for="tipo">Tipo:</label>
                    <select name="tipo" class="form-control">
                        <option value="didatico">Didático</option>
                        <option value="literatura">Literatura</option>
                    </select>
                </div>
                <div class="col-lg-6 col-xs-12">
                    <label for="area_conhecimento">Área de Conhecimento:</label>
                    <select name="area_conhecimento" class="form-control">
                        <option value="informatica">Informática</option>
                        <option value="ciencia">Ciência</option>
                        <option value="suspense">Suspense</option>
                        <option value="romance">Romance</option>
                        <option value="ficcao">Ficção</option>
                    </select>
                </div>
                <div class="col-lg-6 col-xs-12">
                    <label for="data_publicacao">Data de Publicação:</label>
                    <input type="text" name="data_publicacao" id="data_publicacao" class="form-control" required>
                    <script>
                        $('#data_publicacao').datepicker();
                        $('#data_publicacao').datepicker("option","dateFormat","yy-mm-dd");
                    </script>
                </div>
                <div class="col-lg-3 col-xs-12">
                    <label for="valor">Valor:</label>
                    <input type="number" min="0.00" step="0.01" name="valor" value="0.00" class="form-control" required>
                </div>
                <div class="col-lg-3 col-xs-12">
                    <label for="quantidade">Quantidade:</label>
                    <input type="number" min="0" name="quantidade" value="0" class="form-control" required>
                </div>
                <div class="col-lg-12 col-xs-12">
                    <label for="descricao">Descrição:</label>
                    <textarea name="descricao" class="form-control" required></textarea>
                </div>
                <div class="col-lg-12 col-xs-12">
                    <label for="imagem">Imagem:</label>
                    <input type="file" name="imagem" class="form-control" required>
                </div>
                <div class="col-lg-12 col-xs-12">
                    <label for="autor">Autor:</label>
                    <select name="autor" class="form-control">
                        <c:forEach var="autor" items="${sessionScope.livraria.getAutores()}">
                            <option value="${autor.getIdString()}">${autor.getNome()}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="panel-footer col-lg-12 col-xs-12">
                <div class="col-lg-9 col-xs-6"></div>
                <div class="col-lg-3 col-xs-6">
                    <button type="submit" class="btn btn-primary" formaction="adminAdicionarLivro">Adicionar</button>
                </div>
            </div>
        </div>
    </form>
    <div class="col-lg-3 col-xs-0"></div>
</div>
