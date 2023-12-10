package com.springboot.multi.tenant.example.configuration.tenant;

import com.springboot.multi.tenant.example.repository.customer.CustomerModel;
import com.springboot.multi.tenant.example.repository.customer.CustomerRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TenantDataSource {

    private static final Logger logger = LoggerFactory.getLogger(TenantDataSource.class);

    @Autowired
    private CustomerRepository customerRepository;

    private Map<String, DataSource> customerDataSources;

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

    @Bean
    public Map<String, DataSource> loadCustomerDataSource() {
        List<CustomerModel> customers = getCustomers();

        customerDataSources = new HashMap<>();

        for (CustomerModel customer : customers) {
            HikariDataSource customerDataSource = new HikariDataSource();
            customerDataSource.setJdbcUrl(databaseURL + "?currentSchema=" + customer.getDatabaseSchema());
            customerDataSource.setDriverClassName("org.postgresql.Driver");
            customerDataSource.setPassword(databasePassword);
            customerDataSource.setUsername(databaseUser);
            customerDataSource.setMaximumPoolSize(maxPoolSize);
            customerDataSource.setMinimumIdle(minIdlePoolSize);
            customerDataSource.setPoolName("HikariPool(Customer " + customer.getId() + ")");

            try {
                customerDataSource.getConnection();
            } catch (SQLException ex) {
                logger.error("Failed to create connection for HikariPool (Customer " + customer.getId() + ")");
            }

            customerDataSources.put(customer.getId().toString(), customerDataSource);
        }


        return customerDataSources;
    }

    @Bean
    public List<CustomerModel> getCustomers() {
        Iterable<CustomerModel> iterable = customerRepository.findAll();
        List<CustomerModel> customers = new ArrayList<>();
        iterable.forEach(customers::add);

        return customers;
    }

    public Map<String, DataSource> getCustomerDataSources() {
        return customerDataSources;
    }
}
