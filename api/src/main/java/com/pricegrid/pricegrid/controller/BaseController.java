package com.pricegrid.pricegrid.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "*")
@RestController
public class BaseController {

    @GetMapping("/")
    public String Home() {
        return "Hello! the API's are working fine please use /api/prices";
    }

}
