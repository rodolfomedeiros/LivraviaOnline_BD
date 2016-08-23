package livraria.util;

public class ConnectionFactoryException extends RuntimeException{
    public ConnectionFactoryException() {
        super("Problema com a conexão com o banco de dados!");
    }
}