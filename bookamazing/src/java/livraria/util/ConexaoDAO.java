package livraria.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.negocio.Livro;
import livraria.negocio.Autor;
import livraria.negocio.Compra;
import livraria.servlet.AdminControlServlet;


public class ConexaoDAO {
    public ArrayList<Livro> getLivros(){
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "select * from livro";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            ResultSet r = stmt.executeQuery();
            ArrayList<Livro> livros = new ArrayList();
            
            while(r.next()){
                livros.add(new Livro(r.getInt("id"),r.getString("titulo"),r.getString("descricao"),r.getString("tipo"),r.getString("area_conhecimento"),r.getString("data_publicacao"),r.getFloat("valor"),r.getInt("quantidade"), r.getString("caminho_imagem"), r.getInt("id_autor")));
            }
            
            if(!r.next()){
                new ConnectionFactory().connectionClose(con, stmt, r);
                return livros;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminControlServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public Livro getLivro(int idLivro){
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "select * from livro where id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, idLivro);
            
            ResultSet r = stmt.executeQuery();
            Livro livro = null;
            
            while(r.next()){
                livro = new Livro(r.getInt("id"),r.getString("titulo"),r.getString("descricao"),r.getString("tipo"),r.getString("area_conhecimento"),r.getString("data_publicacao"),r.getFloat("valor"),r.getInt("quantidade"), r.getString("caminho_imagem"), r.getInt("id_autor"));
            }
            
            if(!r.next()){
                new ConnectionFactory().connectionClose(con, stmt, r);
                return livro;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminControlServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public ArrayList<Autor> getAutores(){
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "select * from autor";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            ResultSet r = stmt.executeQuery();
            ArrayList<Autor> autores = new ArrayList();
            
            while(r.next()){
                autores.add(new Autor(r.getString("id"), r.getString("nome")));
            }
            
            if(!r.next()){
                new ConnectionFactory().connectionClose(con, stmt, r);
                return autores;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminControlServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public boolean updateLivro(Livro l){
        boolean r = false;
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "update livro set titulo=?, descricao=?, tipo=?, area_conhecimento=?, data_publicacao=?,"+
                            "valor=?, quantidade=?, caminho_imagem=?, id_autor=? where id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, l.getTitulo());
            stmt.setString(2, l.getDescricao());
            stmt.setString(3, l.getTipo());
            stmt.setString(4, l.getArea_conhecimento());
            stmt.setString(5, l.getData_publicacao());
            stmt.setFloat(6, l.getValor());
            stmt.setInt(7, l.getQuantidade());
            stmt.setString(8, l.getCaminho_imagem());
            stmt.setInt(9, l.getIdAutor());
            stmt.setInt(10, l.getId());

            r = stmt.execute();

            new ConnectionFactory().connectionClose(con, stmt);

        } catch (SQLException | ConnectionFactoryException ex) {
            System.out.println(ex.getMessage());
        }

        return r;
    }
    
    public ArrayList<Livro> buscarLivrosTipo(String tipo){
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "select * from livro where tipo=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, tipo);
            
            ResultSet r = stmt.executeQuery();
            ArrayList<Livro> livros = new ArrayList();
            
            while(r.next()){
                livros.add(new Livro(r.getInt("id"),r.getString("titulo"),r.getString("descricao"),r.getString("tipo"),r.getString("area_conhecimento"),r.getString("data_publicacao"),r.getFloat("valor"),r.getInt("quantidade"), r.getString("caminho_imagem"), r.getInt("id_autor")));
            }
            
            if(!r.next()){
                new ConnectionFactory().connectionClose(con, stmt, r);
                return livros;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return null;
    }
    
    public ArrayList<Livro> buscarLivrosTipoAndArea(String tipo, String area){
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "select * from livro where tipo=? and area_conhecimento=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, tipo);
            stmt.setString(2, area);
            
            ResultSet r = stmt.executeQuery();
            ArrayList<Livro> livros = new ArrayList();
            
            while(r.next()){
                livros.add(new Livro(r.getInt("id"),r.getString("titulo"),r.getString("descricao"),r.getString("tipo"),r.getString("area_conhecimento"),r.getString("data_publicacao"),r.getFloat("valor"),r.getInt("quantidade"), r.getString("caminho_imagem"), r.getInt("id_autor")));
            }
            
            if(!r.next()){
                new ConnectionFactory().connectionClose(con, stmt, r);
                return livros;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return null;
    }
    
    public ArrayList<Compra> buscarCompras(int id_pedido){
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "select * from compra where id_pedido=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, id_pedido);
            
            ResultSet r = stmt.executeQuery();
            ArrayList<Compra> compras = new ArrayList();
            
            while(r.next()){
                Compra c = new Compra(r.getInt("id_pedido"), r.getInt("id_livro"), r.getInt("quantidade_livro"), r.getFloat("valor"));
                compras.add(c);
            }
            
            
            new ConnectionFactory().connectionClose(con, stmt, r);
                
            return compras;
        }catch(SQLException | ConnectionFactoryException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
