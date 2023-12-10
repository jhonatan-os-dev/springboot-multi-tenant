package com.springboot.multi.tenant.example.repository.customer;

import com.springboot.multi.tenant.example.repository.customer.CustomerModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerModel, Long> {
}
