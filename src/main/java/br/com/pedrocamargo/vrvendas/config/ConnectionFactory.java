package br.com.pedrocamargo.vrvendas.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private EnvConfig envConfig;
    private String host;
    private String porta;
    private String nomeBd;
    private String usuario;
    private String senha;

    public ConnectionFactory(){
        this.envConfig = new EnvConfig();
        this.host = envConfig.getBancoDadosHost();
        this.porta = envConfig.getBancoDadosPorta();
        this.usuario = envConfig.getBancoDadosUsuario();
        this.senha = envConfig.getBancoDadosSenha();
        this.nomeBd = envConfig.getBancoDadosNome();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://"+host+":"+porta+"/"+nomeBd+"?user="+usuario+"&password="+senha);
    }
}