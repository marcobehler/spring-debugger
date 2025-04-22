package org.example.springdebugger.service.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionLevel7Service {

    private static final Logger log = LoggerFactory.getLogger(TransactionLevel7Service.class);
    private final CustomerService customerService;

    @Autowired
    public TransactionLevel7Service(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Transactional
    public void processTransactionLevel7(String password) {
        log.info("Processing transaction at level 7");
        customerService.createRandomCustomer(password);
    }
}