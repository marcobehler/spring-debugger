package org.example.springdebugger.service.customer;

import org.example.springdebugger.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CustomerHelper {

    private static final Logger log = LoggerFactory.getLogger(CustomerHelper.class);

    public void trace(List<Customer> all) {
        log.info("Tracing Customers: " + all);
    }
}
