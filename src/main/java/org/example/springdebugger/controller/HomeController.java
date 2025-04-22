package org.example.springdebugger.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.springdebugger.service.IpAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final IpAddressService ipAddressService;
    
    @Autowired
    public HomeController(IpAddressService ipAddressService) {
        this.ipAddressService = ipAddressService;
    }
    
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        // Hello world message
        String message = "Hello, World!";
        
        // My name (hardcoded for demonstration)
        String name = "Spring Developer";
        
        // Get client's IP address using the service
        String ipAddress = ipAddressService.resolveClientIpAddress(request);
        
        // Add attributes to the model
        model.addAttribute("message", message);
        model.addAttribute("name", name);
        model.addAttribute("ipAddress", ipAddress);
        
        // Return the view name (index.html)
        return "index";
    }
}