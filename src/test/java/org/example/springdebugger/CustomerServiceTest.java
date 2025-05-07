package org.example.springdebugger;

import org.example.springdebugger.model.Customer;
import org.example.springdebugger.repository.CustomerRepository;
import org.example.springdebugger.service.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Container
    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest");

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        // The database is initialized with data.sql, but we can add additional setup if needed
    }

    @Test
    @Transactional
    void testCreateRandomCustomer() {
        // Get initial count
        int initialCount = customerService.getAllCustomers().size();
        // Create a random customer
        customerService.createRandomCustomer("testPassword123");

        // Get updated list
        List<Customer> customers = customerService.getAllCustomers();
        
        // Verify a new customer was added
        assertEquals(initialCount + 1, customers.size());
        
        // Find the newly added customer (should be the last one)
        Customer newCustomer = customers.get(customers.size() - 1);
        
        // Verify customer properties
        assertNotNull(newCustomer.getId());
        assertTrue(newCustomer.getUsername().startsWith("user_"));
        assertTrue(newCustomer.getEmail().endsWith("@example.com"));
        assertEquals("testPassword123", newCustomer.getPassword());
        assertNotNull(newCustomer.getFirstName());
        assertNotNull(newCustomer.getLastName());
        assertNotNull(newCustomer.getActive());
    }

    @Test
    void testGetAllCustomers() {
        // Get all customers
        List<Customer> customers = customerService.getAllCustomers();
        
        // Verify we have at least the sample data from data.sql
        assertTrue(customers.size() >= 4);
        
        // Verify some of the sample data
        boolean foundAdmin = false;
        boolean foundJohnDoe = false;
        
        for (Customer customer : customers) {
            if ("admin".equals(customer.getUsername())) {
                foundAdmin = true;
                assertEquals("admin@example.com", customer.getEmail());
                assertEquals("Admin", customer.getFirstName());
                assertEquals("User", customer.getLastName());
                assertTrue(customer.getActive());
            } else if ("johndoe".equals(customer.getUsername())) {
                foundJohnDoe = true;
                assertEquals("john.doe@example.com", customer.getEmail());
                assertEquals("John", customer.getFirstName());
                assertEquals("Doe", customer.getLastName());
                assertTrue(customer.getActive());
            }
        }
        
        assertTrue(foundAdmin, "Admin user not found");
        assertTrue(foundJohnDoe, "John Doe user not found");
    }
}