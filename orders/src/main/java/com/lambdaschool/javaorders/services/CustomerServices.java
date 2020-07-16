package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;

import java.util.List;

public interface CustomerServices
{
    Customer save(Customer customer);

    List<Customer> findAllCustomers();

    Customer findById(long id);

    Customer findByCustnameIgnoringCase(String custname);

    List<Customer> findByNameLike(String custname);

    void delete(long custcode);

    Customer update(Customer customer, long custcode);
}
