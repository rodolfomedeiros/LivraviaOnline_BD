<c:if test="${sessionScope.admin != true}">
    <script>
        window.location.assign("/bookamazing/view/clienteIndex");
    </script>
</c:if>
<div class="container-fluid">
    <div class="col-lg-3"></div>
    <form>
    <div class="panel panel-default col-lg-6 col-xs-12">
        <div class="panel-heading col-lg-12 col-xs-12">SELECIONE O LIVRO:</div>
        <div class="panel-body col-lg-12 col-xs-12">
            <select name="remover" class="form-control" required>
                <c:forEach var="livro" items="${sessionScope.livraria.getLivros()}">
                    <option value="${livro.getId()}">${livro.getTitulo()}</option>
                </c:forEach>
            </select>
        </div>
        <div class="panel-footer col-lg-12 col-xs-12">
            <div class="col-lg-10 col-xs-8"></div>
            <div class="col-lg-2 col-xs-4">
                <button class="btn btn-warning" type="submit" formaction="adminRemoverLivro">Remover</button>
            </div>
        </div>
    </div>
    </form>
    <div class="col-lg-3"></div>
</div>
