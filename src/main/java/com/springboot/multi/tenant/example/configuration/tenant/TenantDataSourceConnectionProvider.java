package com.springboot.multi.tenant.example.configuration.tenant;


import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class TenantDataSourceConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    boolean init = false;

    @Autowired
    private DataSource defaultDS;

    @Autowired
    private ApplicationContext context;

    private Map<String, DataSource> map;

    @PostConstruct
    public void load() {
        map = new HashMap<>();
        map.put("default", defaultDS);
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return map.get("default");
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        if (!init) {
            init = true;
            TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
            map.putAll(tenantDataSource.getCustomerDataSources());
        }

        if (map.get(tenantIdentifier) != null) {
            return map.get(tenantIdentifier);
        }

        throw new RuntimeException("Customer not configured");
    }
}
