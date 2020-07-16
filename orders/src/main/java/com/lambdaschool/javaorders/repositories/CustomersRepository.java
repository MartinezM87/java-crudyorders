package com.lambdaschool.javaorders.repositories;

import com.lambdaschool.javaorders.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomersRepository extends CrudRepository<Customer, Long>
{
    Customer findByCustnameIgnoringCase(String custname);

    List<Customer> findByCustnameContainingIgnoreCase(String subname);
}
