package com.lambdaschool.javaorders.controllers;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController
{
    @Autowired
    private CustomerServices customerServices;

    // GET
    //    http://localhost:2019/customers/orders
    @GetMapping(value = "/orders",
        produces = {"application/json"})
    public ResponseEntity<?> listAllCustomers()
    {
        List<Customer> myCustomers = customerServices.findAllCustomers();
        return new ResponseEntity<>(myCustomers,
            HttpStatus.OK);
    }

    //    http://localhost:2019/customers/customer/7
    //    http://localhost:2019/customers/customer/77
    @GetMapping(value = "/customer/{custcode}",
        produces = "application/json")
    public ResponseEntity<?> findCustomerById(
        @PathVariable
            long custcode)
    {
        Customer c = customerServices.findById(custcode);
        return new ResponseEntity<>(c,
            HttpStatus.OK);
    }

    //    http://localhost:2019/customers/namelike/mes
    //    http://localhost:2019/customers/namelike/cin
    @GetMapping(value = "/namelike/{subname}",
        produces = "application/json")
    public ResponseEntity<?> findByNameLike(
        @PathVariable
            String subname)
    {
        List<Customer> nameList = customerServices.findByNameLike(subname);
        return new ResponseEntity<>(nameList,
            HttpStatus.OK);
    }

    // POST
    // POST http://localhost:2019/customers/customer
    @PostMapping(value = "/customer",
        consumes = "application/json")
    public ResponseEntity<?> addNewCustomer(
        @Valid
        @RequestBody
            Customer newCustomer)
    {
        newCustomer.setCustcode(0);
        newCustomer = customerServices.save(newCustomer);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerUri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{custcode}")
            .buildAndExpand(newCustomer.getCustcode())
            .toUri();
        responseHeaders.setLocation(newCustomerUri);
        return new ResponseEntity<>(null,
            responseHeaders,
            HttpStatus.CREATED);
    }

    // PUT
    // PUT http://localhost:2019/customers/customer/19
    @PutMapping(value = "customer/{custcode}",
        produces = "application/json",
        consumes = "application/json")
    public ResponseEntity<?> updateCustomer(
        @Valid
        @RequestBody
            Customer updateCustomer,
        @PathVariable
            long custcode)
    {
        updateCustomer.setCustcode(custcode);
        updateCustomer = customerServices.save(updateCustomer);

        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

    // PATCH
    // PATCH http://localhost:2019/customers/customer/19
    // Request Body - JSON Object
    @PatchMapping(value = "/customer/{custcode}",
    consumes = "application/json")
    public ResponseEntity<?> updatePartCustomer(@RequestBody Customer updateCustomer,
                                                    @PathVariable long custcode)
    {
        customerServices.update(updateCustomer, custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // DELETE
    // DELETE http://localhost:2019/customers/customer/54
    @DeleteMapping(value = "/customer/{custcode}")
    public ResponseEntity<?> deleteCustomerById(
        @PathVariable
            long custcode)
    {
        customerServices.delete(custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
