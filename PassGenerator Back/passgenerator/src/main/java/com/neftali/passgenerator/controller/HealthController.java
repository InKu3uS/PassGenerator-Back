package com.neftali.passgenerator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "health")
public class HealthController {

    @GetMapping
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("OK");
    }
}
