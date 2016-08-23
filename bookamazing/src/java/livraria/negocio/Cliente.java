package livraria.negocio;

public class Cliente {
    private String login;
    private String senha;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    
    public Cliente(){}
    
    public Cliente(String login){
        setLogin(login);
    }
    
    public Cliente(String login, String senha, String nome, String cpf, String email, String telefone){
        setLogin(login);
        setSenha(senha);
        setNome(nome);
        setCpf(cpf);
        setEmail(email);
        setTelefone(telefone);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
