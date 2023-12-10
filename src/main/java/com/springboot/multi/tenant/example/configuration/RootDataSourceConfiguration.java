package com.springboot.multi.tenant.example.configuration;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.springboot.multi.tenant.example.repository.customer", entityManagerFactoryRef =
        "rootEntityManagerFactory", transactionManagerRef = "rootTransactionManager")
public class RootDataSourceConfiguration {

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private Integer maxPoolSize;

    @Value("${spring.datasource.hikari.minimum-idle}")
    private Integer minIdlePoolSize;

    @Value("${database.url}")
    private String databaseURL;

    @Value("${database.user}")
    private String databaseUser;

    @Value("${database.password}")
    private String databasePassword;

    @Value("${database.schema.root}")
    private String getDatabaseSchemaRoot;

    @Primary
    @Bean
    public DataSource rootDataSource() {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(databaseURL + "?currentSchema=" + getDatabaseSchemaRoot);
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setPassword(databasePassword);
        dataSource.setUsername(databaseUser);
        dataSource.setMaximumPoolSize(maxPoolSize);
        dataSource.setMinimumIdle(minIdlePoolSize);
        dataSource.setPoolName("HikariPool(Default)");

        return dataSource;

    }

    @Primary
    @Bean(name = "rootEntityManagerFactory")

    public LocalContainerEntityManagerFactoryBean rootEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(rootDataSource()).packages("com.springboot.multi.tenant.example.repository.customer")
                .build();
    }

    @Primary
    @Bean(name = "rootTransactionManager")
    public PlatformTransactionManager rootTransactionManager(
            final @Qualifier("rootEntityManagerFactory") LocalContainerEntityManagerFactoryBean rootEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(rootEntityManagerFactory.getObject()));
    }

}
