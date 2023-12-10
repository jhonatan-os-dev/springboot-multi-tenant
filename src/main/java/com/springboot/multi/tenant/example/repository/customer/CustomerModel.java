package com.springboot.multi.tenant.example.repository.customer;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "customer")
@Table(name = "customer")
public class CustomerModel {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "database_schema")
    private String databaseSchema;
}
