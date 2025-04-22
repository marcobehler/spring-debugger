package org.example.springdebugger.service.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionLevel5Service {

    private static final Logger log = LoggerFactory.getLogger(TransactionLevel5Service.class);
    private final TransactionLevel6Service transactionLevel6Service;

    @Autowired
    public TransactionLevel5Service(TransactionLevel6Service transactionLevel6Service) {
        this.transactionLevel6Service = transactionLevel6Service;
    }

    @Transactional
    public void processTransactionLevel5() {
        log.info("Processing transaction at level 5");
        transactionLevel6Service.processTransactionLevel6();
    }
}