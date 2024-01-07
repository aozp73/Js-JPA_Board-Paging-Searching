package com.example.demo.module;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String list() {
        log.debug("GET - Home 페이지");
        return "pages/home";
    }

}
