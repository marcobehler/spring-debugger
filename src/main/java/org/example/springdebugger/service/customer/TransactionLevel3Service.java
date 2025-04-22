package org.example.springdebugger.service.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionLevel3Service {

    private static final Logger log = LoggerFactory.getLogger(TransactionLevel3Service.class);
    private final TransactionLevel4Service transactionLevel4Service;

    @Autowired
    public TransactionLevel3Service(TransactionLevel4Service transactionLevel4Service) {
        this.transactionLevel4Service = transactionLevel4Service;
    }

    @Transactional
    public void processTransactionLevel3() {
        log.info("Processing transaction at level 3");
        transactionLevel4Service.processTransactionLevel4();
    }
}