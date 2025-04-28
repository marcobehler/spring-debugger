package org.example.springdebugger.service.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionLevel4Service {

    private static final Logger log = LoggerFactory.getLogger(TransactionLevel4Service.class);
    private final TransactionLevel5Service transactionLevel5Service;
    private final CustomerService customerService;


    @Autowired
    public TransactionLevel4Service(TransactionLevel5Service transactionLevel5Service, CustomerService customerService) {
        this.transactionLevel5Service = transactionLevel5Service;
        this.customerService = customerService;
    }

    @Transactional
    public void processTransactionLevel4(String password) {
        log.info("Processing transaction at level 4");
        customerService.createRandomCustomer(password);
        transactionLevel5Service.processTransactionLevel5(password);
    }
}