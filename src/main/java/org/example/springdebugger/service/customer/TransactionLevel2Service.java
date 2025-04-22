package org.example.springdebugger.service.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionLevel2Service {

    private static final Logger log = LoggerFactory.getLogger(TransactionLevel2Service.class);
    private final TransactionLevel3Service transactionLevel3Service;

    @Autowired
    public TransactionLevel2Service(TransactionLevel3Service transactionLevel3Service) {
        this.transactionLevel3Service = transactionLevel3Service;
    }

    @Transactional
    public void processTransactionLevel2(String password) {
        log.info("Processing transaction at level 2");
        transactionLevel3Service.processTransactionLevel3(password);
    }
}