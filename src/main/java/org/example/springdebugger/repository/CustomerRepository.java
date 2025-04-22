package org.example.springdebugger.repository;

import org.example.springdebugger.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // Spring Data JPA will automatically implement basic CRUD operations
    // We can add custom query methods here if needed in the future
}