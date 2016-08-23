<div class="container-fluid">
    <c:if test="${sessionScope.loginCliente == false}">
        <div class="row">
            <div class="alert alert-danger col-lg-4 col-lg-offset-4 col-xs-12">
                <a href="#" class="close" data-dismiss="alert" aria-label="close"><span class="glyphicon glyphicon-remove"></span></a>
                <span class="small">Login ou senha inválidos!</span>
            </div>
        </div>
        ${sessionScope.loginCliente = null}
    </c:if>
    <div class="panel panel-default col-lg-4 col-xs-12 col-lg-offset-4">
        <form method="post">
            <div class="panel-heading">
                <h5 class="small">Faça o Login!</h5>
            </div>
            <div class="panel-body">
                <label for="login">Login:</label>
                <input class="form-control" name="login" type="text">
                <label for="login">Senha:</label>
                <input class="form-control" name="senha" type="password">
            </div>
            <div class="col-lg-12 col-md-12 col-xs-12">
                <div class="col-lg-9 col-md-9 col-xs-9"></div>
                <div class="col-lg-3 col-md-3 col-xs-3">
                   <button class="btn btn-primary" type="submit" formaction="login">Entrar</button> 
                </div>
            </div>
            <div class="col-lg-12 col-md-12 col-xs-12 text-center well-sm">
                <a href="createCliente" class="btn btn-link">Ainda não é um cliente? Clique Aqui!</a>
            </div>
        </form>
    </div>
</div>