package org.tinywind.webtemplate.config;

import org.tinywind.webtemplate.util.jooq.ExceptionTranslator;
import org.jooq.ExecuteListener;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySource("classpath:maven.properties")
public class PersistenceConfig extends AbstractPersistenceConfig {
    @Value("${jdbc.driverClass}")
    private String driverClass;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.user}")
    private String user;
    @Value("${jdbc.password}")
    private String password;
    @Value("${jdbc.dialect}")
    private String dialect;

    @Bean
    public DefaultDSLContext dsl() {
        return new DefaultDSLContext(configuration());
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    private DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(transactionAwareDataSource());
    }

    private ExecuteListener exceptionTranslator() {
        return new ExceptionTranslator();
    }

    private org.jooq.Configuration configuration() {
        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.setSQLDialect(SQLDialect.valueOf(dialect.toUpperCase()));
        configuration.setConnectionProvider(connectionProvider());
        configuration.setExecuteListenerProvider(new DefaultExecuteListenerProvider(exceptionTranslator()));
        return configuration;
    }

    public String getDriverClass() {
        return this.driverClass;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    public String getDialect() {
        return this.dialect;
    }
}
