<c:if test="${sessionScope.cliente == null && sessionScope.endereco == null}">
    <script>
        window.location.assign("/bookamazing/view/clienteIndex");
    </script>
</c:if>
<div class="container-fluid">
    <div class="well col-lg-4 col-xs-12 col-lg-offset-4">
        <h2 class="text-center text-success">Compra Finalizada!</h2>
        <p class='small text-center'>Verifique seu email, enviamos o comprovante!</p>
    </div>
</div>