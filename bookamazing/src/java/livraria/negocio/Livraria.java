package livraria.negocio;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

import livraria.util.ConexaoDAO;
import org.json.JSONException;

public class Livraria {
    private ArrayList<Livro> livros;
    private ArrayList<Autor> autores;

    public Livraria() {
        livros = new ArrayList();
    }
    
    public void setLivros(ArrayList<Livro> livros){
        this.livros = livros;
    }
    
    public void setAutores(ArrayList<Autor> autores){
        this.autores = autores;
    }
    
    public List<Livro> getLivros() {
        return livros;
    }

    public Livro getLivro(String idLivro){
        Livro livroProcurado = null;
        for (Livro book : livros) {
            if (book.getIdString().equals(idLivro)) {
                livroProcurado = book;
            }
        }

        return livroProcurado;
    }
    
    public Autor getAutor(String idAutor){
        for (Autor autor : getAutores()) {
            if (autor.getIdString().equals(idAutor)) {
                return autor;
            }
        }
        return null;
    }
    
    public JSONObject getLivroJSON(String idLivro) throws JSONException{
        JSONObject objLivro = new JSONObject();
        for (Livro book : livros) {
            if (book.getIdString().equals(idLivro)) {
                objLivro.put("idLivro", book.getIdString());
                objLivro.put("titulo", book.getTitulo());
                objLivro.put("descricao", book.getDescricao());
                objLivro.put("tipo", book.getTipo());
                objLivro.put("area_conhecimento", book.getArea_conhecimento());                       
                objLivro.put("valor", book.getValorString());
                objLivro.put("quantidade", book.getQuantidade());
                objLivro.put("caminho_imagem", book.getCaminho_imagem());
                objLivro.put("nome_autor", getAutor(String.valueOf(book.getIdAutor())).getNome());
                
                return objLivro;
            }
        }
        
        return null;
    }
    
    public ArrayList<Livro> verificarQuantidadeLivros(ArrayList<Livro> livrosCart){
        ArrayList<Livro> r = new ArrayList();
        for (Livro l : livrosCart) {
            if(l.getQuantidade() > getLivro(l.getIdString()).getQuantidade()){
                r.add(getLivro(l.getIdString()));
            }
        }
        
        return r;
    }
    
    public ArrayList<Livro> comprarLivros(ArrayList<Livro> livros){
        ArrayList<Livro> r = new ArrayList();
        
        //reduz a quantidade
        for(Livro l : livros){
           Livro ll = getLivro(l.getIdString());
           ll.setQuantidade(ll.getQuantidade()-l.getQuantidade());
           r.add(ll);
        }
        //atualiza os livros
        for(Livro l : r){
           new ConexaoDAO().updateLivro(l);
        }

        return r;
    }

    public ArrayList<Autor> getAutores() {
        return autores;
    }

}
