package org.example.springdebugger.service.customer;

import org.example.springdebugger.model.Customer;

import java.util.List;

public class CustomerHelper {

    public void trace(List<Customer> all) {
        System.out.println("trace");
    }
}
