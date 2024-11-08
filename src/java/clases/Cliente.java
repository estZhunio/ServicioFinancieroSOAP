package clases;

public class Cliente {
    private String id_cli;
    private String usuario_Cli;
    private String password_cli;
    private double saldo_cli;

    public Cliente() {
    }

    public Cliente(String usuario_Cli, String password_cli, double saldo_cli) {
        this.usuario_Cli = usuario_Cli;
        this.password_cli = password_cli;
        this.saldo_cli = saldo_cli;
    }

    public String getUsuario_Cli() {
        return usuario_Cli;
    }

    public void setUsuario_Cli(String usuario_Cli) {
        this.usuario_Cli = usuario_Cli;
    }

    public String getPassword_cli() {
        return password_cli;
    }

    public void setPassword_cli(String password_cli) {
        this.password_cli = password_cli;
    }

    public double getSaldo_cli() {
        return saldo_cli;
    }

    public void setSaldo_cli(double saldo_cli) {
        this.saldo_cli = saldo_cli;
    }  

    public String getId_cli() {
        return id_cli;
    }

    public void setId_cli(String id_cli) {
        this.id_cli = id_cli;
    }
    
    
}
