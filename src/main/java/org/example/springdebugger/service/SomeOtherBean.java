package org.example.springdebugger.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SomeOtherBean {

    @Transactional
    public int getAge() {
        throw new RuntimeException("some exception");
    }
}
