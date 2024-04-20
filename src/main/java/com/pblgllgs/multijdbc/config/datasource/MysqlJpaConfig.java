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
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.pblgllgs.multijdbc.persistence.mysql.repository",
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTransactionManager"
)
@EnableTransactionManagement
public class MysqlJpaConfig {

    @Bean("mysqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            @Qualifier("mysqlDataSource") DataSource mysqlDS,
            EntityManagerFactoryBuilder builder
    ) {
        Map<String, String> additionalMysqlProperties = new HashMap<>();
        additionalMysqlProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        return builder.dataSource(mysqlDS)
                .persistenceUnit("mysql")
                .properties(additionalMysqlProperties)
                .packages("com.pblgllgs.multijdbc.persistence.mysql.entity")
                .build();
    }

    @Bean("mysqlTransactionManager")
    public JpaTransactionManager jpaTransactionManager(
            @Qualifier("mysqlEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean
    ) {
        return new JpaTransactionManager(Objects.requireNonNull(localContainerEntityManagerFactoryBean.getObject()));
    }

}
