package livraria.servlet;

import livraria.util.ConexaoDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import livraria.negocio.Autor;
import livraria.negocio.Livraria;
import livraria.negocio.Livro;
import livraria.util.ConnectionFactory;

public class AdminControlServlet extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        String idLivro;
        
        Livraria livrariaMain;
        synchronized(session){
            livrariaMain = (Livraria) session.getAttribute("livraria");
        }
        if(livrariaMain == null){
            livrariaMain = new Livraria();
            synchronized(session){
                session.setAttribute("livraria", livrariaMain);
            }
        }
        
        String acaoSelecionada = request.getServletPath();
        
        switch (acaoSelecionada) {
            case "/view/adminRemoverLivro":
                idLivro = request.getParameter("remover");
                if(idLivro != null){
                    if(removerLivro(Integer.parseInt(idLivro))){
                        session.setAttribute("adminLivroRemovido", true);
                    }else{
                        session.setAttribute("adminLivroRemovido", false);
                    }
                }
                livrariaMain.setLivros(new ConexaoDAO().getLivros());
                break;
            case "/view/adminAdicionarLivro":
                try {
                    if(request.getParameter("titulo") != null){
                        Part img = request.getPart("imagem");
                        img.write(img.getSubmittedFileName());
                        
                        String titulo = request.getParameter("titulo");
                        String descricao = request.getParameter("descricao");
                        String tipo = request.getParameter("tipo");
                        String area_conhecimento = request.getParameter("area_conhecimento");
                        String data_publicacao = request.getParameter("data_publicacao");
                        String valor = request.getParameter("valor");
                        String quantidade = request.getParameter("quantidade");
                        String caminho_imagem = img.getSubmittedFileName();
                        
                        img.delete();
                        
                        String id_autor = request.getParameter("autor");
                        
                        Livro l = new Livro(titulo, descricao, tipo, area_conhecimento, data_publicacao, valor, quantidade, caminho_imagem);
                        
                        if(inserirLivro(l,Integer.parseInt(id_autor))){
                            session.setAttribute("adminLivroInserido", true);
                        }else{
                            session.setAttribute("adminLivroInserido", false);
                        }
                        livrariaMain.setLivros(new ConexaoDAO().getLivros());
                    }
                    
                } catch (IOException | ServletException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case "/view/adminAtualizarLivro":
                idLivro = request.getParameter("idLivro");
                try{
                    if(idLivro != null){
                        Part img = request.getPart("imagem");
                        img.write(img.getSubmittedFileName());
                        
                        int id = Integer.parseInt(idLivro);
                        String titulo = request.getParameter("titulo");
                        String descricao = request.getParameter("descricao");
                        String tipo = request.getParameter("tipo");
                        String area_conhecimento = request.getParameter("area_conhecimento");
                        String data_publicacao = request.getParameter("data_publicacao");
                        float valor = Float.parseFloat(request.getParameter("valor"));
                        int quantidade = Integer.parseInt(request.getParameter("quantidade"));
                        String caminho_imagem = img.getSubmittedFileName();
                        img.delete();
                        
                        int id_autor = Integer.parseInt(request.getParameter("autor"));
                        
                        Livro l = new Livro(id,titulo, descricao, tipo, area_conhecimento, data_publicacao, valor, quantidade, caminho_imagem,id_autor);
                        
                        if(atualizarLivro(l)){
                            session.setAttribute("adminLivroAtualizado", true);
                        }else{
                            session.setAttribute("adminLivroAtualizado", false);
                        }
                        livrariaMain.setLivros(new ConexaoDAO().getLivros());
                    }
                } catch (IOException | ServletException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case "/view/adminInserirAutor":
                String nome = request.getParameter("nome");
                if(nome != null){
                    inserirAutor(nome);
                }
                break;
            case "/view/adminIndex":
                //buscar
                String tipo = request.getParameter("tipo");
                String area = request.getParameter("area");
                if(tipo != null){
                    if(area != null){
                        livrariaMain.setLivros(new ConexaoDAO().buscarLivrosTipoAndArea(tipo, area));
                    }else{
                        livrariaMain.setLivros(new ConexaoDAO().buscarLivrosTipo(tipo));
                    }
                }else{
                    livrariaMain.setLivros(new ConexaoDAO().getLivros());
                }
                
        }
        
        
        livrariaMain.setAutores(new ConexaoDAO().getAutores());
        
        //redireciona...
        try {
            request.getRequestDispatcher(acaoSelecionada+".jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            System.out.println("erro durante dispatcher da page..." + ex.getMessage());
        }
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){        
        doGet(request, response);
    }
    
    ArrayList<Autor> getAutores(){
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "select * from autor";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            ResultSet r = stmt.executeQuery();
            ArrayList<Autor> autores = new ArrayList();
            
            while(r.next()){
                autores.add(new Autor(r.getString("id"),r.getString("nome")));
            }
            
            if(!r.next()){
                new ConnectionFactory().connectionClose(con, stmt, r);
                return autores;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return null;
    }
    
    private boolean atualizarLivro(Livro l){
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "update livro set titulo=?,descricao=?,tipo=?,area_conhecimento=?,data_publicacao=?,valor=?,quantidade=?,caminho_imagem=?,id_autor=? where id=?";
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
            
            boolean r = stmt.execute();
            
            new ConnectionFactory().connectionClose(con, stmt);
            
            return !r;
            
        } catch (SQLException ex) {
            System.out.println("Deu erro no banco");
        }
        return false;
    }
    
    private boolean inserirLivro(Livro l, int autor){
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "insert into livro values(null,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, l.getTitulo());
            stmt.setString(2, l.getDescricao());
            stmt.setString(3, l.getTipo());
            stmt.setString(4, l.getArea_conhecimento());
            stmt.setString(5, l.getData_publicacao());
            stmt.setFloat(6, l.getValor());
            stmt.setInt(7, l.getQuantidade());
            stmt.setString(8, l.getCaminho_imagem());
            stmt.setInt(9, autor);
            
            boolean r = stmt.execute();
            
            new ConnectionFactory().connectionClose(con, stmt);
            
            return !r;
            
        } catch (SQLException ex) {
            System.out.println("Deu erro no banco");
        }
        return false;
    }
    
    private boolean inserirAutor(String nome){
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "insert into autor values(null,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, nome);
            
            boolean r = stmt.execute();
            
            new ConnectionFactory().connectionClose(con, stmt);
            
            return !r;
            
        } catch (SQLException ex) {
            System.out.println("Deu erro no banco");
        }
        return false;
    }
    
    private boolean removerLivro(int idLivro){
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "delete from livro where id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, idLivro);
            
            boolean r = stmt.execute();
            
            new ConnectionFactory().connectionClose(con, stmt);
            
            return !r;
        } catch (SQLException ex) {
            Logger.getLogger(AdminControlServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
