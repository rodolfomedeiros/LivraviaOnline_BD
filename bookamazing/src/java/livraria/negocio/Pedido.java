package livraria.negocio;

public class Pedido {
    private int id;
    private String login_cliente;
    private String data_compra;
    private String data_entrega;
    private boolean pedido_entregue;
    
    public Pedido(int id, String login_cliente, String data_compra, String data_entrega, boolean pedido_entregue){
        setId(id);
        setLogin_cliente(login_cliente);
        setData_compra(data_compra);
        setData_entrega(data_entrega);
        setPedido_entregue(pedido_entregue);
    }

    public int getId() {
        return id;
    }
    
    public String getIdString() {
        return String.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin_cliente() {
        return login_cliente;
    }

    public void setLogin_cliente(String login_cliente) {
        this.login_cliente = login_cliente;
    }

    public String getData_compra() {
        return data_compra;
    }

    public void setData_compra(String data_compra) {
        this.data_compra = data_compra;
    }

    public String getData_entrega() {
        return data_entrega;
    }

    public void setData_entrega(String data_entrega) {
        this.data_entrega = data_entrega;
    }

    public boolean isPedido_entregue() {
        return pedido_entregue;
    }

    public void setPedido_entregue(boolean pedido_entregue) {
        this.pedido_entregue = pedido_entregue;
    }
}
