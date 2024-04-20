package com.pblgllgs.multijdbc.config.datasource;
/*
 *
 * @author pblgl
 * Created on 20-04-2024
 *
 */

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.HashMap;

@Configuration
public class AppConfig {


    @Bean
    public org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder getEntityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                new HashMap<>(),
                null
        );
    }
}
