package com.example.cloudnative;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class exController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "Hell2222o";
    }
}
