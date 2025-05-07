package org.example.springdebugger.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class SomeBean {

    private final SomeOtherBean someOtherBean;

    public SomeBean(SomeOtherBean someOtherBean) {
        this.someOtherBean = someOtherBean;
    }

    @Transactional
    public String getName() {
        System.out.println(" TransactionSynchronizationManager.isActualTransactionActive() = " + TransactionSynchronizationManager.isActualTransactionActive());
        System.out.println("here my transaction should be fine");
        System.out.println("isMarkedForRollback = " +  TransactionAspectSupport.currentTransactionStatus().isRollbackOnly());

        try {
            someOtherBean.getAge();
        } catch (RuntimeException e) {
            System.out.println("isMarkedForRollback = " + TransactionAspectSupport.currentTransactionStatus().isRollbackOnly());
            System.out.println("here my transaction should be marked as rolled back and be visible as such");
        }
        return "John";
    }
}