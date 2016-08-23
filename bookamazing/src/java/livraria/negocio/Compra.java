package livraria.negocio;

import livraria.util.FerramentaUtil;

public class Compra {
    private int id_pedido;
    private int id_livro;
    private int quantidade_livros;
    private float valor;
    
    public Compra(int id_pedido, int id_livro, int quantidade_livros, float valor){
        setId_pedido(id_pedido);
        setId_livro(id_livro);
        setQuantidade_livros(quantidade_livros);
        setValor(valor);
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_livro() {
        return id_livro;
    }

    public void setId_livro(int id_livro) {
        this.id_livro = id_livro;
    }

    public int getQuantidade_livros() {
        return quantidade_livros;
    }

    public void setQuantidade_livros(int quantidade_livros) {
        this.quantidade_livros = quantidade_livros;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
    
    public float getTotalValor(){
        return quantidade_livros*valor;
    }
    
    public String getTotalValorString(){
        return new FerramentaUtil().floatToStringFormatter(getTotalValor());
    }
}
