<c:if test="${sessionScope.admin != true}">
    <script>
        window.location.assign("/bookamazing/view/clienteIndex");
    </script>
</c:if>
<div class="container-fluid">
    <div class="col-lg-3 col-xs-0"></div>
    <form method="post">       
        <div class="panel panel-default col-lg-6 col-xs-12">
            <div class="panel-heading text-center col-lg-12 col-xs-12"><h6 class="text-negrito">INFORME O NOME DO AUTOR!</h6></div>
            <div class="panel-body col-lg-12 col-xs-12">
                <div class="col-lg-12 col-xs-12">
                    <label for="nome">Nome:</label>
                    <input type="text" name="nome" class="form-control" required>
                </div>
            </div>
            <div class="panel-footer col-lg-12 col-xs-12">
                <div class="col-lg-9 col-xs-6"></div>
                <div class="col-lg-3 col-xs-6">
                    <button type="submit" class="btn btn-primary" formaction="adminInserirAutor">Inserir</button>
                </div>
            </div>
        </div>
    </form>
    <div class="col-lg-3 col-xs-0"></div>
</div>
