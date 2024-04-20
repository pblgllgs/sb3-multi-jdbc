# Spring boot multi database connector.

## Beans

- EntityManagerFactoryBuilder
- Datasource(MySQL and PostgreSQL)
- Properties

```java
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
```

```java
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
```

## Start databases

```bash
docker compose up -d
```

```bash
docker-compose down -v
```
## MySQL

- EntityManagerFactory
- JpaTransactionManager

```java
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
```

## PostgreSQL

- EntityManagerFactory
- JpaTransactionManager

```java
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
```