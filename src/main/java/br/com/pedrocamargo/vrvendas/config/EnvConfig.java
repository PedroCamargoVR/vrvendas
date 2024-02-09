package br.com.pedrocamargo.vrvendas.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
    private Dotenv dotenv = Dotenv.configure().load();
    private String bdHost = dotenv.get("DATABASE_HOST");
    private String bdPorta = dotenv.get("DATABASE_PORT");
    private String bdUsuario = dotenv.get("DATABASE_USER");
    private String bdSenha = dotenv.get("DATABASE_PASSWORD");
    private String bdNome = dotenv.get("DATABASE_NAME");

    public String getBancoDadosHost() {
        return bdHost;
    }

    public String getBancoDadosPorta() {
        return bdPorta;
    }

    public String getBancoDadosUsuario() {
        return bdUsuario;
    }

    public String getBancoDadosSenha() {
        return bdSenha;
    }
    
    public String getBancoDadosNome(){
        return bdNome;
    }
}
