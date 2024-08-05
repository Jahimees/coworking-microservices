package by.jahimees.coworking.ui.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Value("${page.main}")
    private String MAIN_PAGE;

    @GetMapping("/main")
    public String getMainPage() {
        return MAIN_PAGE;
    }
}
