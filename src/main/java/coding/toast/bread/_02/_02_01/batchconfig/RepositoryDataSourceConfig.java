package coding.toast.bread._02._02_01.batchconfig;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class RepositoryDataSourceConfig {

    @Bean
    public DataSource repositoryDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName("org.postgresql.Driver");
        hikariDataSource.setJdbcUrl("jdbc:postgresql://localhost:10011/postgres");
        hikariDataSource.setUsername("postgres");
        hikariDataSource.setPassword("postgres");
        hikariDataSource.setSchema("batch_meta");
        hikariDataSource.setPoolName("repositoryDataSource");
        return hikariDataSource;
    }

    @Bean
    public PlatformTransactionManager repositoryDataSourceTx() {
        return new DataSourceTransactionManager(repositoryDataSource());
    }

}
