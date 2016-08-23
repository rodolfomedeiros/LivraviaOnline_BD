<c:if test="${sessionScope.cliente == null && sessionScope.endereco == null}">
    <script>
        window.location.assign("/bookamazing/view/clienteIndex");
    </script>
</c:if>
<div class="container-fluid">
    <div class="panel-group col-lg-6 col-lg-offset-3 col-xs-12" id="accordion" role="tablist">
        <c:forEach var="pedido" items="${sessionScope.meusPedidos.getPedidos()}">
            <div class="panel panel-default col-lg-12 col-xs-12">
                <div class="panel-heading" role="tab">
                    <a class='btn btn-link' role="button" data-toggle="collapse" data-parent="#accordion" href="#${pedido.getId()}">
                        Pedido Nº${pedido.getId()}
                    </a>
                </div>
                <div id='${pedido.getId()}' class="panel-collapse collapse" role="tabpanel">
                    <div class="panel-body col-lg-12 col-xs-12 text-justify">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>Produto</th>
                                    <th class="text-center">#</th>
                                    <th class="text-center">Valor</th>
                                    <th class="text-center">Total</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="livro" items="${sessionScope.meusPedidos.getCompras(pedido.getId())}">
                                    <tr>
                                        <td class="col-lg-6"><em>${livro.getTitulo()}</em></td>
                                        <td class="col-lg-1 text-center small">${livro.getQuantidade()}</td>
                                        <td class="col-lg-2 text-center small">${livro.getValorString()} R$</td>
                                        <td class="col-lg-3 text-center small">${livro.getValorTotalString()} R$</td>
                                    </tr>
                                </c:forEach> 
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td class="text-right small">
                                        <p class="text-negrito">Subtotal:</p>
                                        <p class="text-negrito">Frete:</p>
                                    </td>
                                    <td class="text-center small">
                                        <c:set var="valorTotalPedido" value="${sessionScope.meusPedidos.getValorTotalString(pedido.getId())}"></c:set>
                                        <p class='text-negrito'>${valorTotalPedido} R$</p>
                                        <p class='text-negrito'>0.00 R$</p>
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td class="text-right"><h4 class="text-negrito">Total: </h4></td>
                                    <td class="text-center text-success"><h4>${valorTotalPedido} R$</h4></td>
                                </tr>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td>
                                        <p><span class="text-negrito">Data da Compra: </span>${pedido.getData_compra()}</p>
                                        <p><span class="text-negrito">Data de Entrega: </span>${pedido.getData_entrega()}</p>
                                        <c:choose>
                                            <c:when test="${pedido.isPedido_entregue()}">
                                                <p><span class="text-negrito">Pedido Entregue: </span>Sim</p>
                                            </c:when>
                                            <c:otherwise>
                                                <p><span class="text-negrito">Pedido Entregue: </span>Não</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

