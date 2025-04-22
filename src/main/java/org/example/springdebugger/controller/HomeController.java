package org.example.springdebugger.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.springdebugger.service.CustomerService;
import org.example.springdebugger.service.IpAddressService;
import org.example.springdebugger.service.TransactionLevel1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final IpAddressService ipAddressService;
    private final CustomerService customerService;
    private final TransactionLevel1Service transactionLevel1Service;

    @Value("${developer.name:Default Developer}")
    private String developerName;

    @Autowired
    public HomeController(IpAddressService ipAddressService, CustomerService customerService, TransactionLevel1Service transactionLevel1Service) {
        this.ipAddressService = ipAddressService;
        this.customerService = customerService;
        this.transactionLevel1Service = transactionLevel1Service;
    }

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        // Hello world message
        String message = "Welcome to my next AI Startup!";

        // My name (read from properties)
        String name = developerName;

        // Get client's IP address using the service
        String ipAddress = ipAddressService.resolveClientIpAddress(request);

        // Get all customers
        var customers = customerService.getAllCustomers();

        // Add attributes to the model
        model.addAttribute("message", message);
        model.addAttribute("name", name);
        model.addAttribute("ipAddress", ipAddress);
        model.addAttribute("customers", customers);

        // Return the view name (index.html)
        return "index";
    }

    @PostMapping("/add-random-customer")
    public String addRandomCustomer() {
        transactionLevel1Service.startTransactionChain();
        return "redirect:/";
    }


}
