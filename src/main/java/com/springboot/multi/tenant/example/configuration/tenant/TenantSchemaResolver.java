package com.springboot.multi.tenant.example.configuration.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

    @Autowired
    private TenantContext tenantContext;

    @Override
    public String resolveCurrentTenantIdentifier() {
        String t = tenantContext.getCurrentTenant();
        String defaultTenant = "default";
        return Objects.requireNonNullElse(t, defaultTenant);
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
