package coding.toast.bread._02._02_01.batchconfig;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

// @Configuration
public class RepositoryDataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName("org.postgresql.Driver");
        hikariDataSource.setJdbcUrl("jdbc:postgresql://localhost:10011/postgres");
        hikariDataSource.setUsername("postgres");
        hikariDataSource.setPassword("postgres");
        hikariDataSource.setPoolName("repositoryDataSource");
        return hikariDataSource;
    }

    @Bean
    @Primary
    // transactionManager 라는 명칭을 사용하면 SimpleBatchConfigurer 와 충돌이 생깁니다. 이름을 억지로 바꾸시기 바랍니다!
    public PlatformTransactionManager batchProjectGlobalTxManager() {
        return new DataSourceTransactionManager(dataSource());
    }


    @Bean
    // @BatchDataSource
    public DataSource batchDataSource() {
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
    public PlatformTransactionManager batchProjectMetaDataTxManager() {
        return new DataSourceTransactionManager(batchDataSource());
    }
    
    // 사실은 거창하게 CustomBatchConfigurer 를 만들지 않아도 아래처럼 가볍게 DatgSource 만 변경할 수도 있습니다...만!
    // 좀 더 세세한 제어를 하려면 CustomBatchConfigurer 를 사용하는 게 맞습니다.
    // @Bean
    // public BatchConfigurer configurer(@Qualifier("batchDataSource") DataSource dataSource) {
    //     return new DefaultBatchConfigurer(dataSource);
    // }

}
