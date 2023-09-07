package coding.toast.bread._02._02_01.batchconfig;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.DatabaseType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

// https://medium.com/official-podo/spring-batch-%EB%A1%9C-%EB%8B%A4%EC%A4%91-data-source-%EC%A0%91%EA%B7%BC%ED%95%98%EA%B8%B0-%EB%A7%A4%EC%9A%B0-%EA%B0%84%EB%8B%A8-%EC%A3%BC%EC%9D%98-7332f2a5f7f8
@Configuration
public class CustomBatchConfigurer extends DefaultBatchConfigurer {

    private final DataSource dataSource;
    private final PlatformTransactionManager txManager;

    public CustomBatchConfigurer(@Qualifier("repositoryDataSource") DataSource dataSource,
                                 @Qualifier("repositoryDataSourceTx") PlatformTransactionManager txManager) {
        this.dataSource = dataSource;
        this.txManager = txManager;
    }

    @Override
    protected JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDatabaseType(DatabaseType.POSTGRES.name());
        factory.setTablePrefix("BATCH_");
        factory.setDataSource(this.dataSource);
        factory.setTransactionManager(this.txManager);
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}
