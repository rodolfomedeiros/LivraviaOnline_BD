package livraria.negocio;

import java.util.ArrayList;
import java.util.HashMap;
import livraria.util.FerramentaUtil;

public class CarrinhoCompras {
    private HashMap<String, Livro> livros;
    
    public CarrinhoCompras() {
        livros = new HashMap();
    }

    public synchronized void adicionar(Livro livro) {
        if (livros.containsKey(livro.getIdString())) {
            livros.get(livro.getIdString()).more();
        } else {
            livro.setQuantidade(1);
            livros.put(livro.getIdString(), livro);
        }
    }

    public synchronized void remover(String idLivro) {
        if (livros.containsKey(idLivro)) {
            Livro livro = livros.get(idLivro);
            if (livro.getQuantidade() <= 0) {
                livros.remove(idLivro);
            }
        }
    }
    
    //retirar da hash caso a quantidade seja menor que zero..
    private synchronized void verificarQuantidade(String idLivro) {
        if (livros.containsKey(idLivro)) {
            Livro livro = livros.get(idLivro);
            if (livro.getQuantidade() <= 0) {
                livros.remove(idLivro);
            }
        }
    }

    public synchronized ArrayList<Livro> getLivros() {
        ArrayList<Livro> resultado = new ArrayList();
        resultado.addAll(this.livros.values());
        return resultado;
    }

    public synchronized int getNumeroLivros() {
        return livros.size();
    }
    
    public synchronized int getTotalQuantidade() {
        int total = 0;
        total = getLivros().stream().map((livro) -> livro.getQuantidade()).reduce(total, Integer::sum);
        return total;
    }

    public synchronized String getTotalValorString() {
        float total = (float) 0.0;
        total = getLivros().stream().map((livro) -> (livro.getQuantidade() * livro.getValor())).reduce(total, (accumulator, _item) -> accumulator + _item);
        return  new FerramentaUtil().floatToStringFormatter(total);
    }
    
    public synchronized float getTotalValor() {
        float total = (float) 0.0;
        total = getLivros().stream().map((livro) -> (livro.getQuantidade() * livro.getValor())).reduce(total, (accumulator, _item) -> accumulator + _item);
        return  total;
    }

    public synchronized void limpar() {
        livros.clear();
    }
    
    public synchronized void aumentarQuantidade(String idLivro) {
        if (livros.containsKey(idLivro)) { 
            livros.get(idLivro).more();
        }
    }
    
    public synchronized void diminuirQuantidade(String idLivro) { 
        if (livros.containsKey(idLivro)) { 
            livros.get(idLivro).less();
            verificarQuantidade(idLivro);
        }
    }
}
