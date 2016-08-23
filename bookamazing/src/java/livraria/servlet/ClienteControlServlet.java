package livraria.servlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import livraria.negocio.CarrinhoCompras;
import livraria.negocio.Cliente;
import livraria.negocio.Compra;
import livraria.negocio.Endereco;
import livraria.negocio.Livraria;
import livraria.negocio.Livro;
import livraria.negocio.MeusPedidos;
import livraria.negocio.Pedido;
import livraria.util.ConexaoDAO;
import livraria.util.ConnectionFactory;
import livraria.util.ConnectionFactoryException;
import livraria.util.SendEmail;


public class ClienteControlServlet extends HttpServlet {
    //page para email...
    String head = null;
    String footer = null;
    
    @Override
    public void init(ServletConfig servletConfig){
        head = servletConfig.getInitParameter("head");
        footer = servletConfig.getInitParameter("footer");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<Livro> r;
        
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
            case "/view/clienteIndex":
                //adicionando no carrinho
                String addCart = request.getParameter("addCart");
                if(addCart != null){
                    Livro l = livrariaMain.getLivro(addCart);
                    if(l != null){
                        cart.adicionar(l);
                    }
                }
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
                break;
            case "/view/clienteCarrinho":
                String atr = request.getParameter("atr");
                String idLivro = request.getParameter("id");
                if(atr != null && idLivro != null){
                    if(atr.equals("1")){
                        cart.aumentarQuantidade(idLivro);
                    }
                    if(atr.equals("-1")){
                        cart.diminuirQuantidade(idLivro);
                    }
                }
                break;
            case "/view/clienteFinalizarCompra":
                livrariaMain.setLivros(new ConexaoDAO().getLivros());
                r = livrariaMain.verificarQuantidadeLivros(cart.getLivros());
                
                if(r.size() > 0){
                    session.setAttribute("livrosDisponivel", r);
                    acaoSelecionada = "/view/clienteCarrinho";
                }
                break;
            case "/view/clienteRecibo":
                livrariaMain.setLivros(new ConexaoDAO().getLivros());
                r = livrariaMain.verificarQuantidadeLivros(cart.getLivros());
                
                if(r.size() > 0){
                    session.setAttribute("livrosQuantidadeDisponivel", r);
                    acaoSelecionada = "/view/clienteCarrinho";
                }else{
                    r = livrariaMain.comprarLivros(cart.getLivros());
                    if(!r.isEmpty()){
                        r = cart.getLivros();
                        Cliente cliente = (Cliente) session.getAttribute("cliente");
                        Endereco endereco = (Endereco) session.getAttribute("endereco");
                        Date d = new Date();
                        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                        String dataCompra = ft.format(d);
                        
                        //inserindo pedido
                        Pedido pedido = new Pedido(0, cliente.getLogin(), dataCompra, dataCompra, true);
                        
                        if(inserirPedido(pedido)){
                            pedido.setId(getIdPedido(pedido));
                        }
                        
                        //inserindo as compras
                        System.out.println("Número do Pedido: "+pedido.getId());
                        if(pedido.getId() != 0){
                            for(Livro l : r){
                                Compra compra = new Compra(pedido.getId(), l.getId(), l.getQuantidade(), l.getValor());
                                inserirCompra(compra);
                            }
                        }
                        
                        //envia email de confirmação
                        new SendEmail().sendHtmlEmail(cliente.getEmail(), getHtmlEmailPage(getStringHtml(head),getStringHtml(footer),cart, endereco));

                        session.setAttribute("cart", null);
                    }else{
                        acaoSelecionada = "/view/clienteCarrinho";
                    }
                }
                break;
            case "/view/clienteContaExcluida":
                Cliente c = (Cliente) session.getAttribute("cliente");
                if(deleteCliente(c.getLogin()) && deleteEndereco(c.getLogin())){
                    synchronized(session){
                        session.invalidate();
                        session = request.getSession();
                        cart = new CarrinhoCompras();
                        session.setAttribute("cart", cart);
                        livrariaMain.setLivros(new ConexaoDAO().getLivros());
                        session.setAttribute("livraria", livrariaMain);
                    }
                }
                break;
            case "/view/clienteMeusPedidos":
                Cliente clienteMeusPedidos = (Cliente) session.getAttribute("cliente");
                MeusPedidos mPedidos = new MeusPedidos();
                
                //quando set pedidos, também seta as compas para cada pedido...
                mPedidos.setPedidos(buscarPedidos(clienteMeusPedidos.getLogin()));
                
                session.setAttribute("meusPedidos", mPedidos);
                
                break;
        }
        
        livrariaMain.setAutores(new ConexaoDAO().getAutores());
        
        //redireciona...
        try {
            request.getRequestDispatcher(acaoSelecionada+".jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            System.out.println("erro durante dispatcher da page..." + ex.getMessage());
        }
    }
    
    private boolean inserirCompra(Compra c){
        boolean r = false;
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "insert into compra values (?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, c.getId_pedido());
            stmt.setInt(2, c.getId_livro());
            stmt.setInt(3, c.getQuantidade_livros());
            stmt.setFloat(4, c.getValor());
            
            r = stmt.execute();
            
            new ConnectionFactory().connectionClose(con, stmt);
            
            return !r;
            
        } catch (SQLException | ConnectionFactoryException ex) {
            System.out.println(ex.getMessage());
        }
        return r;
    }
    
    private boolean inserirPedido(Pedido p){
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "insert into pedido values (null,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, p.getLogin_cliente());
            stmt.setString(2, p.getData_compra());
            stmt.setString(3, p.getData_entrega());
            stmt.setBoolean(4, p.isPedido_entregue());
            
            boolean r = stmt.execute();
            
            new ConnectionFactory().connectionClose(con, stmt);
           
            return !r;
            
        } catch (SQLException | ConnectionFactoryException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    private int getIdPedido(Pedido p){
        try(Connection c = new ConnectionFactory().getConnection()){
            String sql = "select id from pedido where login_cliente=? order by id desc limit 1";
            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setString(1, p.getLogin_cliente());

            ResultSet r = stmt.executeQuery();
            int retorno = 0;
            while(r.next()){
                retorno = r.getInt("id");
            }

            new ConnectionFactory().connectionClose(c, stmt, r);

            return retorno;
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteControlServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
    private ArrayList<Pedido> buscarPedidos(String login_cliente){
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "select * from pedido where login_cliente=?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, login_cliente);

            ResultSet r = stmt.executeQuery();
            ArrayList<Pedido> pedidos = new ArrayList();
            
            while(r.next()){
                pedidos.add(new Pedido(r.getInt("id"), r.getString("login_cliente"), r.getString("data_compra"), r.getString("data_entrega"), r.getBoolean("pedido_entregue")));
            }

            new ConnectionFactory().connectionClose(con, stmt, r);

            return pedidos;
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteControlServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private boolean deleteCliente(String login){
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "delete from cliente where login=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, login);

            boolean r = stmt.execute();
            
            new ConnectionFactory().connectionClose(con, stmt);
            
            return !r;
        } catch (SQLException | ConnectionFactoryException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    private boolean deleteEndereco(String login){
        try(Connection con = new ConnectionFactory().getConnection()){
            String sql = "delete from endereco where login_cliente=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, login);

            boolean r = stmt.execute();
            
            new ConnectionFactory().connectionClose(con, stmt);
            
            return !r;
        } catch (SQLException | ConnectionFactoryException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public String getStringHtml(String path) {
        try {
            String r = "";
            
            BufferedReader html = new BufferedReader(new FileReader(path));
            
            while(html.ready()){
                r = r + html.readLine();
            }
            
            return r;
        } catch (IOException ex) {
            Logger.getLogger(ClienteControlServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private String getHtmlEmailPage(String head, String footer,CarrinhoCompras cart, Endereco endereco){
        String htmlPage;
        
        htmlPage = head;
        
        ArrayList<Livro> livros = cart.getLivros();
        String tableBody = "";
        
        for(Livro l : livros){
            tableBody = tableBody + "<tr>"
                            + "<td style='padding: 8px; line-height: 1.42857143; vertical-align: top; border-top: 1px solid #ddd;'><em>"+l.getTitulo()+"</em></td>"
                            + "<td style='padding: 8px; line-height: 1.42857143; vertical-align: top; border-top: 1px solid #ddd;'>"+l.getQuantidade()+"</td>"
                            + "<td style='padding: 8px; line-height: 1.42857143; vertical-align: top; border-top: 1px solid #ddd;'>"+l.getValorString()+" R$</td>"
                            + "<td style='padding: 8px; line-height: 1.42857143; vertical-align: top; border-top: 1px solid #ddd;'>"+l.getValorTotalString()+" R$</td>"
                        +"</tr>";
        }
        
        tableBody = tableBody + "<tr>"
                        + "<td></td>"
                        + "<td></td>"
                        + "<td style='padding: 8px; line-height: 1.42857143; vertical-align: top; border-top: 1px solid #ddd; text-align: right;'>"
                            + "<p><strong>Subtotal: </strong></p>"
                            + "<p><strong>Frete: </strong></p>"
                        + "</td>"
                        + "<td style='padding: 8px; line-height: 1.42857143; vertical-align: top; border-top: 1px solid #ddd; text-align:center;'>"
                            + "<p><strong>"+cart.getTotalValorString()+" R$</strong></p>"
                            + "<p><strong>0.00 R$</strong></p>"
                        + "</td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td></td>"
                        + "<td></td>"
                        + "<td style='padding: 8px; line-height: 1.42857143; vertical-align: top; border-top: 1px solid #ddd; text-align:right;'><h4><strong>Total: </strong></h4></td>"
                        + "<td style='padding: 8px; line-height: 1.42857143; vertical-align: top; border-top: 1px solid #ddd; text-align:center; color:green;'><h4>"+cart.getTotalValorString()+" R$</h4></td>"
                    + "</tr>"
                + "</tbody>"
                + "<tfoot>"
                    + "<h5><strong>Endereco de Entrega:</strong></h5>"
                    + "<address>"
                        + endereco.getRua()+" - "+ endereco.getNumero()
                        + "<br>"
                        + endereco.getCidade()+" - "+ endereco.getEstado()
                        + "<br>"
                        + "<abbr title='Código Postal'>CEP:</abbr>"+ endereco.getCep()
                    + "</address>"
                + "</tfoot>";

        htmlPage = htmlPage + tableBody + footer;
        return htmlPage;
    }
}
