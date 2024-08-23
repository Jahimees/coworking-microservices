package by.jahimees.coworking.apigateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTestController {


    @GetMapping("/test")
    public String getTest() {
        return "FASFSA";
    }
}
