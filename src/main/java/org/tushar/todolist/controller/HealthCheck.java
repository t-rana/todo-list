package org.tushar.todolist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/health")
public class HealthCheck {

    @GetMapping(value = "/check")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("App is up", HttpStatus.OK);
    }
}
