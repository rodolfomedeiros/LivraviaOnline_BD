<c:if test="${sessionScope.cliente == null && sessionScope.endereco == null}">
    <script>
        window.location.assign("/bookamazing/view/clienteIndex");
    </script>
</c:if>
<div class="container-fluid">
    <div class="col-lg-1 col-xs-0"></div>
    <div class="col-lg-10 col-xs-12">
        <div class="page-header col-lg-12 col-xs-12">
            <h4>Finalizando o pagamento...</h4>
        </div>
        <form>
            <div class="panel-group">
                <div class="panel panel-default col-lg-6 col-xs-12">
                    <div class="panel-heading col-xs-12 col-xs-12">
                        <h3 class="panel-title" >Insira os dados do cartão:</h3>                  
                        <img class="img-responsive pull-right" src="../img_util/logo_operadora.png">            
                    </div>
                    <div class="panel-body col-lg-12 col-xs-12">
                        <div class="col-lg-12 col-xs-12">
                            <div class="form-group">
                                <label for="cardNumber">Número do Cartão:</label>
                                <input type="tel" class="form-control" name="cardNumber" placeholder="XXXXXXXXXXXXXXXX" required autofocus>
                            </div>                            
                        </div>
                        <div class="col-xs-7 col-lg-7">
                            <div class="form-group">
                                <label for="cardExpiry">Data de Expiração:</label>
                                <input type="tel" class="form-control" name="cardExpiry" placeholder="Mês/Ano" required>
                            </div>
                        </div>
                        <div class="col-xs-5 col-lg-5 pull-right">
                            <div class="form-group">
                                <label for="cardCVC">Código CV</label>
                                <input type="tel" class="form-control" name="cardCVC" placeholder="CVC" required>
                            </div>
                        </div>
                    </div>
                </div>
                <%-- CREDIT CARD FORM ENDS HERE --%>
                <div class="panel bg-grey col-lg-6 col-xs-12">
                    <div class="col-xs-12 col-lg-12">
                        <div class="col-xs-12 col-lg-12">
                            <c:set var="endereco" value="${sessionScope.endereco}"></c:set>
                            <h5 class="text-negrito">Endereço de Entrega:</h5>
                            <address>
                                ${endereco.getRua()} - ${endereco.getNumero()}
                                <br>
                                ${endereco.getCidade()} - ${endereco.getEstado()}
                                <br>
                                <abbr title="Código Postal">CEP:</abbr> ${endereco.getCep()}
                                <br>
                                <abbr title="Telefone">Cel:</abbr> ${sessionScope.cliente.getTelefone()} 
                            </address>
                        </div>
                    </div>
                    <div class="text-center">
                        <h2>Informações</h2>
                    </div>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Produto</th>
                                <th>#</th>
                                <th class="text-center">Valor</th>
                                <th class="text-center">Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="livro" items="${sessionScope.cart.getLivros()}">
                                <tr>
                                    <td class="col-lg-7"><em>${livro.getTitulo()}</em></td>
                                    <td class="col-lg-1 text-center small">${livro.getQuantidade()}</td>
                                    <td class="col-lg-1 text-center small">${livro.getValorString()} R$</td>
                                    <td class="col-lg-3 text-center small">${livro.getValorTotalString()} R$</td>
                                </tr>
                            </c:forEach> 
                            <tr>
                                <td>   </td>
                                <td>   </td>
                                <td class="text-right small">
                                    <p class='text-negrito'>Subtotal: </p>
                                    <p class='text-negrito'>Frete: </p>
                                </td>
                                <td class="text-center small">
                                    <p class='text-negrito'>${sessionScope.cart.getTotalValorString()} R$</p>
                                    <p class='text-negrito'>0.00 R$</p>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td class="text-right"><h4 class="text-negrito">Total: </h4></td>
                                <td class="text-center text-success"><h4>${sessionScope.cart.getTotalValorString()} R$</h4></td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="col-lg-12 col-xs-12">
                        <button type="submit" formaction="clienteRecibo" class="btn btn-success btn-lg btn-block"><span class="glyphicon glyphicon-check"></span> Finalizar</span>
                         </button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
