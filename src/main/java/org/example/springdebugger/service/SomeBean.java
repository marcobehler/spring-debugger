package org.example.springdebugger.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.springdebugger.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class SomeBean {

    private final SomeOtherBean someOtherBean;
    private final EntityManager entityManager;


    public SomeBean(SomeOtherBean someOtherBean,  EntityManager entityManager) {
        this.someOtherBean = someOtherBean;
        this.entityManager = entityManager;
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

    @Transactional
    public Customer save(Customer c) {
         entityManager.persist(c);
         return c;
    }
}