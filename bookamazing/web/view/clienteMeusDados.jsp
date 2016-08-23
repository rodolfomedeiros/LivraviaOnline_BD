<c:if test="${sessionScope.cliente == null && sessionScope.endereco == null}">
    <script>
        window.location.assign("/bookamazing/view/clienteIndex");
    </script>
</c:if>
<div class="container-fluid">
    <div class="col-lg-3"></div>
    <form method="post">       
        <div class="panel panel-default col-lg-6 col-xs-12">
            <div class="panel-heading text-center col-lg-12 col-xs-12"><h6 class="text-negrito">MEUS DADOS</h6></div>
            <div class="panel-body col-lg-12 col-xs-12">
                <div class="col-lg-12 col-xs-12">
                    <label for="nome">Nome:</label>
                    <input type="text" name="nome" value="${sessionScope.cliente.getNome()}" class="form-control" disabled>
                </div>
                <div class="col-lg-6 col-xs-6">
                    <label for="cpf">CPF:</label>
                    <input type="tel" name="cpf" class="form-control" value="${sessionScope.cliente.getCpf()}" disabled>
                </div>
                <div class="col-lg-6 col-xs-6">
                    <label for="telefone">Telefone:</label>
                    <input type="tel" name="telefone" class="form-control" value="${sessionScope.cliente.getTelefone()}" disabled>
                </div>
                <div class="col-lg-12 col-xs-12">
                    <label for="email">Email:</label>
                    <input type="email" name="email" class="form-control" value="${sessionScope.cliente.getEmail()}" disabled>
                </div>
                <div class="col-lg-12 col-xs-12">
                    <label for="rua">Rua:</label>
                    <input type="text" name="rua" class="form-control" value="${sessionScope.endereco.getRua()}" disabled>
                </div>
                <div class="col-lg-2 col-xs-6">
                    <label for="numero">Número:</label>
                    <input type="tel" name="numero" class="form-control" value="${sessionScope.endereco.getNumero()}" disabled>
                </div>
                <div class="col-lg-2 col-xs-6">
                    <label for="bairro">Bairro:</label>
                    <input type="text" name="bairro" class="form-control" value="${sessionScope.endereco.getBairro()}" disabled>
                </div>
                <div class="col-lg-3 col-xs-6">
                    <label for="cidade">Cidade:</label>
                    <input type="text" name="cidade" class="form-control" value="${sessionScope.endereco.getCidade()}" disabled>
                </div>
                <div class="col-lg-2 col-xs-6">
                    <label for="estado">Estado:</label>
                    <input type="text" name="estado" class="form-control" value="${sessionScope.endereco.getEstado()}" disabled>
                </div>
                <div class="col-lg-3 col-xs-6">
                    <label for="cep">CEP:</label>
                    <input type="tel" name="cep" class="form-control" value="${sessionScope.endereco.getCep()}" disabled>
                </div>
            </div>
            <div class="panel-footer col-lg-12 col-xs-12">
                <div class="col-lg-8 col-xs-8">
                    <a href="clienteContaExcluida" class="btn btn-danger">Excluir Conta</a>
                </div>
                <div class="col-lg-4 col-xs-4">
                    <button class="btn btn-warning" type="button" id="editarCampos">Editar</button>
                    <button type="submit" class="btn btn-success" formaction="updateCliente">Atualizar</button>
                    <script>
                        $("#editarCampos").on("click", function(){
                            $(".form-control").attr("disabled",false);
                        });
                    </script>
                </div>
            </div>
        </div>
    </form>
    <div class="col-lg-3"></div>
</div>
