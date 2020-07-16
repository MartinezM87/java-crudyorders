package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerService")
public class CustomerServicesImpl implements CustomerServices
{
    @Autowired
    private CustomersRepository custrepos;

    @Transactional
    @Override
    public Customer save(Customer customer)
    {
        Customer newCustomer = new Customer();

        if (customer.getCustcode() != 0)
        {
            custrepos.findById(customer.getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " not found"));

            newCustomer.setCustcode(customer.getCustcode());
        }

        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setAgent(customer.getAgent());

        // OneToMany
        newCustomer.getOrder()
            .clear();
        for (Order o : customer.getOrder())
        {
            Order newOrder = new Order(o.getOrdamount(),
                o.getAdvanceamount(),
                newCustomer,
                o.getOrderdescription());
            newCustomer.getOrder()
                .add(newOrder);
        }

        return custrepos.save(newCustomer);
    }

    @Override
    public List<Customer> findAllCustomers()
    {
        List<Customer> rtnList = new ArrayList<>();

        custrepos.findAll()
            .iterator()
            .forEachRemaining(rtnList::add);

        return rtnList;
    }

    @Override
    public Customer findById(long id)
    {
        Customer c = new Customer();

        return custrepos.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " does not exist!"));
    }

    @Override
    public Customer findByCustnameIgnoringCase(String custname)
    {
        Customer c = custrepos.findByCustnameIgnoringCase(custname);
        if (c == null)
        {
            throw new EntityNotFoundException("Customer " + custname + " not found!");
        }
        return c;
    }

    @Override
    public List<Customer> findByNameLike(String subname)
    {
        return custrepos.findByCustnameContainingIgnoreCase(subname);
    }

    @Override
    public void delete(long custcode)
    {
        custrepos.findById(custcode)
            .orElseThrow(() -> new EntityNotFoundException("Customer " + custcode + " not found"));
        custrepos.deleteById(custcode);
    }

    @Transactional
    @Override
    public Customer update(
        Customer customer,
        long custcode)
    {
        Customer currentCustomer = custrepos.findById(custcode)
            .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " not found"));

        if (customer.getCustname() != null)
        {
            currentCustomer.setCustname(customer.getCustname());
        }

        if (customer.getCustcity() != null)
        {
            currentCustomer.setCustcity(customer.getCustcity());
        }
        if (customer.getWorkingarea() != null)
        {
            currentCustomer.setWorkingarea(customer.getWorkingarea());
        }
        if (customer.getCustcountry() != null)
        {
            currentCustomer.setCustcountry(customer.getCustcountry());
        }
        if (customer.getGrade() != null)
        {
            currentCustomer.setGrade(customer.getGrade());
        }
        if (customer.hasvalueforopeningamt)
        {
            currentCustomer.setOpeningamt(customer.getOpeningamt());
        }
        if (customer.hasvalueforreceiveamt)
        {
            currentCustomer.setReceiveamt(customer.getReceiveamt());
        }
        if (customer.hasvalueforpaymentamt)
        {
            currentCustomer.setPaymentamt(customer.getPaymentamt());
        }
        if (customer.hasvalueforoutstandingamt)
        {
            currentCustomer.setOutstandingamt(customer.getOutstandingamt());
        }
        if (customer.getPhone() != null)
        {
            currentCustomer.setPhone(customer.getPhone());
        }
        if (customer.getAgent() != null)
        {
            currentCustomer.setAgent(customer.getAgent());
        }

        // OneToMany
        if (customer.getOrder()
            .size() > 0)
        {
            currentCustomer.getOrder()
                .clear();
            for (Order o : customer.getOrder())
            {
                Order newOrder = new Order(o.getOrdamount(),
                    o.getAdvanceamount(),
                    currentCustomer,
                    o.getOrderdescription());
                currentCustomer.getOrder()
                    .add(newOrder);
            }
        }
        return custrepos.save(currentCustomer);
    }
}
