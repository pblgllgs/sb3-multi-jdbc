package com.pblgllgs.multijdbc.config.datasource;
/*
 *
 * @author pblgl
 * Created on 20-04-2024
 *
 */


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.pblgllgs.multijdbc.persistence.postgresql.repository",
        entityManagerFactoryRef = "postgresqlEntityManagerFactory",
        transactionManagerRef = "postgresqlTransactionManager"
)
@EnableTransactionManagement
public class PostgresqlJpaConfig {

    @Bean("postgresqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
            @Qualifier("postgresqlDataSource") DataSource postgresqlDS,
            EntityManagerFactoryBuilder builder
    ) {
        Map<String, String> additionalPostgresqlProperties = new HashMap<>();
        additionalPostgresqlProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return builder.dataSource(postgresqlDS)
                .persistenceUnit("postgresql")
                .properties(additionalPostgresqlProperties)
                .packages("com.pblgllgs.multijdbc.persistence.postgresql.entity")
                .build();
    }

    @Bean("postgresqlTransactionManager")
    public JpaTransactionManager jpaTransactionManager(
            @Qualifier("postgresqlEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean
    ) {
        return new JpaTransactionManager(Objects.requireNonNull(localContainerEntityManagerFactoryBean.getObject()));
    }

}
