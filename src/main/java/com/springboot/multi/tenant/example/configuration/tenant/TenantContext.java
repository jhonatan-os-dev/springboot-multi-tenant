package com.springboot.multi.tenant.example.configuration.tenant;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TenantContext {

    private ThreadLocal<String> currentTenant;

    @PostConstruct
    private void init() {
        currentTenant = new InheritableThreadLocal<>();
    }

    public String getCurrentTenant() {
        return currentTenant.get();
    }

    public void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }

    public void clear() {
        currentTenant.set(null);
    }
}
