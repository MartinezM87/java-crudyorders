package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.models.Payment;
import com.lambdaschool.javaorders.repositories.OrdersRepository;
import com.lambdaschool.javaorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "orderService")
public class OrderServicesImpl implements OrderServices
{
    @Autowired
    private OrdersRepository ordersrepos;

    @Autowired
    private PaymentRepository paymentrepos;

    @Transactional
    @Override
    public Order save(Order order)
    {
        Order newOrder = new Order();

        if (order.getOrdnum() != 0)
        {
            ordersrepos.findById(order.getOrdnum())
                .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " not found"));

            newOrder.setOrdnum(order.getOrdnum());
        }

        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setCustomer(order.getCustomer());
        newOrder.setOrderdescription(order.getOrderdescription());

        // ManyToMany
        newOrder.getPayments()
            .clear();
        for (Payment p : order.getPayments())
        {
            Payment newPay = paymentrepos.findById(p.getPaymentid())
                .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found"));

            newOrder.getPayments()
                .add(newPay);
        }
        return ordersrepos.save(newOrder);
    }

    @Override
    public Order findById(long id)
    {
        Order o = new Order();

        return ordersrepos.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order " + id + " does not exist!"));
    }

    @Override
    public void delete(long ordnum)
    {
        ordersrepos.findById(ordnum)
            .orElseThrow(() -> new EntityNotFoundException("Order " + ordnum + " not found"));
        ordersrepos.deleteById(ordnum);
    }
}
