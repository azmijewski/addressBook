package com.zmijewski.adam.addressbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(){
        return "index";
    }
    @GetMapping("/")
    public String firstPage(){
        return"firstPage";
    }
    @PostMapping("/home")
    public String loginSuccess(){
        return "index";
    }
}
