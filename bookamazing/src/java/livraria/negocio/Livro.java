package livraria.negocio;

import livraria.util.FerramentaUtil;

public class Livro {
    private int id;
    private String titulo;
    private String descricao;
    private String tipo;
    private String area_conhecimento;
    private String data_publicacao;
    private float valor;
    private int quantidade;
    private String caminho_imagem;
    private int idAutor;

    public Livro() {}
    
    public Livro(String titulo, String descricao, String tipo, String area_conhecimento, String data_publicacao, String valor, String quantidade, String caminho_imagem){
        setTitulo(titulo);
        setDescricao(descricao);
        setTipo(tipo);
        setArea_conhecimento(area_conhecimento);
        setData_publicacao(data_publicacao);
        setValor(valor);
        setQuantidade(quantidade);
        setCaminho_imagem(caminho_imagem);
    }
    
    public Livro(int id, String titulo, String descricao, String tipo, String area_conhecimento, String data_publicacao, float valor, int quantidade, String caminho_imagem, int idAutor){
        setId(id);
        setTitulo(titulo);
        setDescricao(descricao);
        setTipo(tipo);
        setArea_conhecimento(area_conhecimento);
        setData_publicacao(data_publicacao);
        setValor(valor);
        setQuantidade(quantidade);
        setCaminho_imagem(caminho_imagem);
        setIdAutor(idAutor);
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
    
    public void setId(String id) {
        this.id = Integer.valueOf(id);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getArea_conhecimento() {
        return area_conhecimento;
    }

    public void setArea_conhecimento(String area_conhecimento) {
        this.area_conhecimento = area_conhecimento;
    }

    public String getData_publicacao() {
        return data_publicacao;
    }

    public void setData_publicacao(String data_publicacao) {
        this.data_publicacao = data_publicacao;
    }

    public float getValor() {
        return valor;
    }
    
    public String getValorString() {
        return new FerramentaUtil().floatToStringFormatter(valor);
    }
    
    public String getValorTotalString() {
        return new FerramentaUtil().floatToStringFormatter(getQuantidade()*getValor());
    }
    
    public float getValorTotal() {
        return getQuantidade()*getValor();
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getCaminho_imagem() {
        return caminho_imagem;
    }

    public void setCaminho_imagem(String caminho_imagem) {
        this.caminho_imagem = caminho_imagem;
    }

    private void setValor(String valor) {
        this.valor = Float.valueOf(valor);
    }

    private void setQuantidade(String quantidade) {
        this.quantidade = Integer.valueOf(quantidade);
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }
    
    public void more(){
        this.quantidade++;
    }
    
    public void less(){
        this.quantidade--;
    }
    
    public Livro clonar(){
        return new Livro(id, titulo, descricao, tipo, area_conhecimento, data_publicacao, valor, quantidade, caminho_imagem, idAutor);
    }
}