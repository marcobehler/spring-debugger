package org.example.springdebugger;

import org.example.springdebugger.model.Customer;
import org.example.springdebugger.service.SomeBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;


@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TransactionalTest {


    @Container
    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest");


    @Autowired
    private SomeBean someBean;

    @Test
    public void testCreateRandomCustomer() {
        someBean.getName();

    }


    @Test
    @Transactional
    public void hello() {
        Customer c = new Customer();
        c.setEmail("hello@hello.de");
        c.setFirstName("John");
        c.setLastName("Doe");
        c.setPassword("<PASSWORD>");
        c.setUsername(RandomStringUtils.randomAlphabetic(10));

        someBean.save(c);
        System.out.println("Insert happened here, all good");

        c.setFirstName("Johnny");
        someBean.save(c);
        System.out.println("Nothing will ever happen now, not immediately flushed and test will rollback");
        // TODO
        // SHOW ME THAT THE CUSTOMER HAS NOT BEEN FLUSHED YET!
    }





}
