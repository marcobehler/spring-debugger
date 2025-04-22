package org.example.springdebugger.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionLevel4Service {

    private static final Logger log = LoggerFactory.getLogger(TransactionLevel4Service.class);
    private final TransactionLevel5Service transactionLevel5Service;

    @Autowired
    public TransactionLevel4Service(TransactionLevel5Service transactionLevel5Service) {
        this.transactionLevel5Service = transactionLevel5Service;
    }

    @Transactional
    public void processTransactionLevel4() {
        log.info("Processing transaction at level 4");
        transactionLevel5Service.processTransactionLevel5();
    }
}