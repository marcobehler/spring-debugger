package org.example.springdebugger.service.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionLevel6Service {

    private static final Logger log = LoggerFactory.getLogger(TransactionLevel6Service.class);
    private final TransactionLevel7Service transactionLevel7Service;

    @Autowired
    public TransactionLevel6Service(TransactionLevel7Service transactionLevel7Service) {
        this.transactionLevel7Service = transactionLevel7Service;
    }

    @Transactional
    public void processTransactionLevel6() {
        log.info("Processing transaction at level 6");
        transactionLevel7Service.processTransactionLevel7();
    }
}