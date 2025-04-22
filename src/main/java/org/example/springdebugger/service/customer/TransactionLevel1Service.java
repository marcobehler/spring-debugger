package org.example.springdebugger.service.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionLevel1Service {

    private static final Logger log = LoggerFactory.getLogger(TransactionLevel1Service.class);
    private final TransactionLevel2Service transactionLevel2Service;

    @Autowired
    public TransactionLevel1Service(TransactionLevel2Service transactionLevel2Service) {
        this.transactionLevel2Service = transactionLevel2Service;
    }

    @Transactional
    public void startTransactionChain(String password) {
        log.info("Starting transaction chain at level 1");
        transactionLevel2Service.processTransactionLevel2(password);
    }
}