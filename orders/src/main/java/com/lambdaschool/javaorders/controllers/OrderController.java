package com.lambdaschool.javaorders.controllers;

import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/orders")
public class OrderController
{
    @Autowired
    private OrderServices orderServices;

    // GET
    //    http://localhost:2019/orders/order/7
    @GetMapping(value = "/order/{ordnum}",
        produces = "application/json")
    public ResponseEntity<?> getOrderById(
        @PathVariable
            long ordnum)
    {
        Order o = orderServices.findById(ordnum);
        return new ResponseEntity<>(o,
            HttpStatus.OK);
    }

    // POST
    // POST http://localhost:2019/orders/order
    @PostMapping(value = "/order",
    consumes = "application/json")
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order newOrder)
    {
        newOrder.setOrdnum(0);
        newOrder = orderServices.save(newOrder);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path("/{ordnum}")
            .buildAndExpand(newOrder.getOrdnum())
            .toUri();
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    // PUT
    // PUT http://localhost:2019/orders/order/63
    @PutMapping(value = "/order/{ordnum}",
    consumes = "application/json")
    public ResponseEntity<?> updateOrder(@Valid @RequestBody Order updateOrder,
                                         @PathVariable long ordnum)
    {
        updateOrder.setOrdnum(ordnum);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // DELETE
    // DELETE http://localhost:2019/orders/order/58
    @DeleteMapping(value = "/order/{ordnum}")
    public ResponseEntity<?> deleteOrderById(
        @PathVariable
            long ordnum)
    {
        orderServices.delete(ordnum);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
