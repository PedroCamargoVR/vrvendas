package br.com.pedrocamargo.vrvendas.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.UUID;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;

public class DatabaseTesteConfig {

    public DatabaseTesteConfig(){}
    
    public static DataSource createDataSource(){
        String jdbcUrl = "jdbc:h2:mem:testdb_" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1";
        
        HikariConfig  dataConfig = new HikariConfig();
        dataConfig.setJdbcUrl(jdbcUrl);
        dataConfig.setUsername("sa");
        dataConfig.setPassword("");
        
        HikariDataSource dataSource = new HikariDataSource(dataConfig);
        
        Flyway flyway = Flyway
                .configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration/test")
                .load();
        flyway.migrate();
        
        return dataSource;
    }
}
