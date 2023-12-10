package com.springboot.multi.tenant.example.configuration.inteceptor;


import com.springboot.multi.tenant.example.configuration.tenant.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    private TenantContext tenantContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

        String requestURI = request.getRequestURI();

        String[] split = requestURI.split("/");

        if (split.length > 3) {
            String tenantID = split[3];

            if (tenantID == null) {
                response.getWriter().write("Not authorized");
                response.setStatus(403);
                return false;
            }
            tenantContext.setCurrentTenant(tenantID);
            return true;

        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) {

        tenantContext.clear();
    }
}
