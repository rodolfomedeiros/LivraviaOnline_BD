
package livraria.negocio;

import java.util.HashMap;
import java.util.ArrayList;
import livraria.util.ConexaoDAO;
import livraria.util.FerramentaUtil;

public class MeusPedidos {
    private HashMap<String,ArrayList<Livro>> compras = new HashMap();;
    private ArrayList<Pedido> pedidos = new ArrayList();

    public ArrayList<Livro> getCompras(int id_pedido) {
        return compras.get(String.valueOf(id_pedido));
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
        
        for(Pedido p : this.pedidos){
            setCompra(p.getId());
        }
    }
    
    public void setCompra(int id_pedido) {
        ArrayList<Compra> c = new ConexaoDAO().buscarCompras(id_pedido);
        ArrayList<Livro> l = new ArrayList();
        
        for(Compra cc : c){
            Livro ll = new ConexaoDAO().getLivro(cc.getId_livro());
            ll.setQuantidade(cc.getQuantidade_livros());
            ll.setValor(cc.getValor());
            l.add(ll);
        }
        
        this.compras.put(String.valueOf(id_pedido), l);
    }
    
    public float getValorTotal(int id_pedido){
        ArrayList<Livro> livros = compras.get(String.valueOf(id_pedido));
        float r = 0.0f;
        
        for(Livro l : livros){
            r = r + l.getValorTotal();
        }
        
        return r;
    }
    
    public String getValorTotalString(int id_pedido){
        ArrayList<Livro> livros = compras.get(String.valueOf(id_pedido));
        float r = 0.0f;
        
        for(Livro l : livros){
            r = r + l.getValorTotal();
        }
        
        return new FerramentaUtil().floatToStringFormatter(r);
    }
}
