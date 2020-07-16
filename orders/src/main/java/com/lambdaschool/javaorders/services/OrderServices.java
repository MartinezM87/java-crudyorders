package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Order;

public interface OrderServices
{
    Order save(Order order);

    Order findById(long id);

    void delete(long ordnum);
}
