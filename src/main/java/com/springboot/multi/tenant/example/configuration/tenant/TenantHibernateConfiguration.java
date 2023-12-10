package com.springboot.multi.tenant.example.configuration.tenant;


import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(basePackages = "com.springboot.multi.tenant.example.repository.user", entityManagerFactoryRef =
        "customerEntityManagerFactory", transactionManagerRef =
        "customerTransactionManager")
public class TenantHibernateConfiguration {

    @Autowired
    private JpaProperties jpaProperties;

    @Bean
    JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean(name = "customerEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(
            DataSource dataSource,
            AbstractDataSourceBasedMultiTenantConnectionProviderImpl multiTenantConnectionProviderImpl,
            CurrentTenantIdentifierResolver currentTenantIdentifierResolverImpl) {

        Map<String, Object> jpaPropertiesMap = new HashMap<>(
                jpaProperties.getProperties());

        jpaPropertiesMap.put(Environment.MULTI_TENANT,
                             MultiTenancyStrategy.DATABASE);
        jpaPropertiesMap.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER,
                             multiTenantConnectionProviderImpl);
        jpaPropertiesMap.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER,
                             currentTenantIdentifierResolverImpl);

        LocalContainerEntityManagerFactoryBean em =
                new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.springboot.multi.tenant.example.repository.*");
        em.setJpaVendorAdapter(this.jpaVendorAdapter());
        em.setJpaPropertyMap(jpaPropertiesMap);
        return em;
    }

    @Bean(name = "customerTransactionManager")
    public PlatformTransactionManager customerTransactionManager(
            final @Qualifier("customerEntityManagerFactory") LocalContainerEntityManagerFactoryBean customerEntityManagerFactory) {
        return new JpaTransactionManager(
                Objects.requireNonNull(customerEntityManagerFactory.getObject()));
    }

}
