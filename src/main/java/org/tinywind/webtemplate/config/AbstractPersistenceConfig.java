package org.tinywind.webtemplate.config;

import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

public abstract class AbstractPersistenceConfig {

    public abstract String getDriverClass();

    public abstract String getUrl();

    public abstract String getUser();

    public abstract String getPassword();

    protected DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(getDriverClass());
        dataSource.setUrl(getUrl());
        dataSource.setUsername(getUser());
        dataSource.setPassword(getPassword());
        return dataSource;
    }

    protected DelegatingDataSource transactionAwareDataSource() {
        return new TransactionAwareDataSourceProxy(dataSource());
    }
}
