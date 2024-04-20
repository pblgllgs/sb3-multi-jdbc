package com.pblgllgs.multijdbc.config.datasource;
/*
 *
 * @author pblgl
 * Created on 20-04-2024
 *
 */

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean("mysqlProperties")
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSourceProperties getMysqlProperties() {
        return new DataSourceProperties();
    }


    @Bean("mysqlDataSource")
    public DataSource getMysqlDataSource() {
        return getMysqlProperties().initializeDataSourceBuilder()
                .build();
    }

    @Bean("postgresqlProperties")
    @ConfigurationProperties(prefix = "spring.datasource.postgresql")
    public DataSourceProperties getPostgresqlProperties() {
        return new DataSourceProperties();
    }


    @Bean("postgresqlDataSource")
    public DataSource getPostgresqlDataSource() {
        return getPostgresqlProperties().initializeDataSourceBuilder()
                .build();
    }

}
