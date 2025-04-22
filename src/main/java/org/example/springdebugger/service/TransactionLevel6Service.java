package org.example.springdebugger.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionLevel6Service {

    private static final Logger log = LoggerFactory.getLogger(TransactionLevel6Service.class);
    private final CustomerService customerService;

    @Autowired
    public TransactionLevel6Service(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Transactional
    public void processTransactionLevel6() {
        log.info("Processing transaction at level 6");
        customerService.createRandomCustomer();
    }
}