package by.jahimees.coworking.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/api/v1/test")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("HELLO WORLD");
    }
}
