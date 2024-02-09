package br.com.pedrocamargo.vrvendas.config;

import java.sql.Connection;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public abstract class BaseTest {
    
    protected DataSource dataSource;
    protected ConnectionFactory mockConnectionFactory;
    protected Connection conn;
    
    @BeforeEach
    public void setup() throws Exception{
        dataSource = DatabaseTesteConfig.createDataSource();
        mockConnectionFactory = Mockito.mock(ConnectionFactory.class);
        Mockito.when(mockConnectionFactory.getConnection()).thenAnswer(invocation -> dataSource.getConnection());
    }
}
