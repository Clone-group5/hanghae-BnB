package com.hanghae.hanghaebnb.common;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthyCheckController {

    @GetMapping
    public ResponseEntity<HttpStatus> healthyCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
