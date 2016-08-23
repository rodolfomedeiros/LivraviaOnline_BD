package livraria.servlet;

import java.io.IOException;

import livraria.negocio.Cliente;
import livraria.negocio.Endereco;
import livraria.util.ConexaoDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import livraria.negocio.CarrinhoCompras;
import livraria.negocio.Livraria;
import livraria.util.ConnectionFactory;
import livraria.util.ConnectionFactoryException;

public class LoginControlServlet extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String login;
        String senha;
        
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
        
        CarrinhoCompras cart;
        synchronized(session){
            cart = (CarrinhoCompras) session.getAttribute("cart");
        }
        if(cart == null){
            cart = new CarrinhoCompras();
            synchronized(session){
                session.setAttribute("cart", cart);
            }
        }
        
        String acaoSelecionada = request.getServletPath();
        
        switch (acaoSelecionada) {
            case "/view/logout":
                synchronized(session){
                    session.invalidate();
                    acaoSelecionada = "/view/clienteIndex";
                    session = request.getSession();
                    cart = new CarrinhoCompras();
                    session.setAttribute("cart", cart);
                    livrariaMain.setLivros(new ConexaoDAO().getLivros());
                    session.setAttribute("livraria", livrariaMain);
                }
                break;
            case "/view/login":
                login = request.getParameter("login");
                senha = request.getParameter("senha");
                if(login != null && senha != null){
                    try {
                        if(isAdmin(login,senha)){
                            acaoSelecionada = "/view/adminIndex";
                            session.setAttribute("admin", true);
                        }else{
                            Cliente c = getCliente(login, senha);
                            if(c != null){
                                synchronized(session){
                                    session.setAttribute("endereco", getEndereco(c.getLogin()));
                                    session.setAttribute("cliente", c);
                                }
                                acaoSelecionada = "/view/clienteIndex";
                            }else{
                                session.setAttribute("loginCliente", false);
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(LoginControlServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }   break;
            case "/view/createCliente":
                if(request.getParameter("nome") != null){
                    login = request.getParameter("login");
                    senha = request.getParameter("senha");
                    String nome = request.getParameter("nome");
                    String cpf = request.getParameter("cpf");
                    String email = request.getParameter("email");
                    String telefone = request.getParameter("telefone");
                    //endereco
                    String rua = request.getParameter("rua");
                    String numero = request.getParameter("numero");
                    String bairro = request.getParameter("bairro");
                    String cidade = request.getParameter("cidade");
                    String estado = request.getParameter("estado");
                    String cep = request.getParameter("cep");
                    
                    Cliente c = new Cliente(login,senha,nome,cpf,email,telefone);
                    Endereco e = new Endereco(rua, numero, bairro, cidade, estado, cep);
                    
                    try {
                        if((inserirCliente(c) && inserirEndereco(login, e))){
                            acaoSelecionada = "/view/clienteIndex";
                        }
                    } catch (SQLException ex) {
                        System.out.println("Erro no cadastro de cliente...");
                    }
                }
                break;
            case "/view/updateCliente":
                if(request.getParameter("nome") != null){
                    String nome = request.getParameter("nome");
                    String cpf = request.getParameter("cpf");
                    String email = request.getParameter("email");
                    String telefone = request.getParameter("telefone");
                    //endereco
                    String rua = request.getParameter("rua");
                    String numero = request.getParameter("numero");
                    String bairro = request.getParameter("bairro");
                    String cidade = request.getParameter("cidade");
                    String estado = request.getParameter("estado");
                    String cep = request.getParameter("cep");
                    
                    Cliente cliente = (Cliente) session.getAttribute("cliente");
                    
                    Cliente c = new Cliente(cliente.getLogin(),cliente.getSenha(),nome,cpf,email,telefone);
                    Endereco e = new Endereco(rua, numero, bairro, cidade, estado, cep);
                    
                    try {
                        if((updateCliente(c) && updateEndereco(c.getLogin(), e))){
                            session.setAttribute("endereco", getEndereco(c.getLogin()));
                            session.setAttribute("cliente", getCliente(c.getLogin(), c.getSenha()));
                            acaoSelecionada = "/view/clienteMeusDados";
                        }
                    } catch (SQLException ex) {
                        System.out.println("Erro no cadastro de cliente...");
                    }
                }
                break;
        }
        
        livrariaMain.setLivros(new ConexaoDAO().getLivros());
        livrariaMain.setAutores(new ConexaoDAO().getAutores());
        
        //redireciona...
        try {
            request.getRequestDispatcher(acaoSelecionada+".jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            System.out.println("erro durante dispatcher da page..." + ex.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
    
    public boolean isAdmin(String login, String senha) throws SQLException {
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "select login from administrador where login=? and senha=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, login);
            stmt.setString(2, senha);
            
            ResultSet result = stmt.executeQuery();
            
            if(result.next()){
                new ConnectionFactory().connectionClose(con,stmt,result);
                return true;
            }
        }catch(ConnectionFactoryException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public Cliente getCliente(String login, String senha) throws SQLException{
        Cliente c = null;
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "select * from cliente where login=? and senha=? ";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, login);
            stmt.setString(2, senha);
            
            ResultSet r = stmt.executeQuery();
            
            if(r.next()){
                c = new Cliente(r.getString("login"),r.getString("senha"), r.getString("nome"), r.getString("cpf"), r.getString("email"), r.getString("telefone"));
                new ConnectionFactory().connectionClose(con,stmt,r);
            }
        }catch(ConnectionFactoryException e){
            System.out.println(e.getMessage());
        }
        return c;
    }
    
    public Endereco getEndereco(String login_cliente) throws SQLException{
        Endereco e = null;
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "select * from endereco where login_cliente=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, login_cliente);
            
            ResultSet r = stmt.executeQuery();
            
            if(r.next()){
                e = new Endereco(r.getString("rua"),r.getString("numero"), r.getString("bairro"), r.getString("cidade"), r.getString("estado"), r.getString("cep"));
                new ConnectionFactory().connectionClose(con,stmt,r);
            }
            
        } catch (ConnectionFactoryException ex){
            System.out.println(ex.getMessage());
        }
        return e;
    }
    
    public boolean inserirCliente(Cliente c) throws SQLException{
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "insert into cliente values (?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, c.getLogin());
            stmt.setString(2, c.getSenha());
            stmt.setString(3, c.getNome());
            stmt.setString(4, c.getCpf());
            stmt.setString(5, c.getEmail());
            stmt.setString(6, c.getTelefone());
            
            boolean r = stmt.execute();
            
            new ConnectionFactory().connectionClose(con, stmt);
            
            return !r;
        }
    }
    
    public boolean inserirEndereco(String login_cliente, Endereco e) throws SQLException{
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "insert into endereco values (?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, login_cliente);
            stmt.setString(2, e.getRua());
            stmt.setString(3, e.getNumero());
            stmt.setString(4, e.getBairro());
            stmt.setString(5, e.getCidade());
            stmt.setString(6, e.getEstado());
            stmt.setString(7, e.getCep());
            
            boolean r = stmt.execute();
            
            new ConnectionFactory().connectionClose(con, stmt);
            
            return !r;
        }
    }
    
    public boolean updateEndereco(String login_cliente, Endereco e) throws SQLException{
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "update endereco set rua=?, numero=?, bairro=?, cidade=?, estado=?, cep=? where login_cliente=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, e.getRua());
            stmt.setString(2, e.getNumero());
            stmt.setString(3, e.getBairro());
            stmt.setString(4, e.getCidade());
            stmt.setString(5, e.getEstado());
            stmt.setString(6, e.getCep());
            stmt.setString(7, login_cliente);
            
            boolean r = stmt.execute();
            
            new ConnectionFactory().connectionClose(con, stmt);
            
            return !r;
        }
    }
    
    public boolean updateCliente(Cliente c) throws SQLException{
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "update cliente set nome=?, cpf=?, email=?, telefone=? where login=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getCpf());
            stmt.setString(3, c.getEmail());
            stmt.setString(4, c.getTelefone());
            stmt.setString(5, c.getLogin());
            
            boolean r = stmt.execute();
            
            new ConnectionFactory().connectionClose(con, stmt);
            
            return !r;
        }
    }
}

